package com.seeu.shopper.ashop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.seeu.oauth.OAuthType;
import com.seeu.shopper.adminorder.model.PriceModel;
import com.seeu.shopper.adminorder.model.PriceModelStatus;
import com.seeu.shopper.adminorder.model.PriceProductModel;
import com.seeu.shopper.adminorder.model.ProductQuantityModelU;
import com.seeu.shopper.ashop.service.UnitService;
import com.seeu.shopper.order.iservice.OrderCreaterService;
import com.seeu.shopper.order.iservice.OrderStatus;
import com.seeu.shopper.order.iservice.PriceAtStockV2Service;
import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.paypal.config.PaypalPaymentIntent;
import com.seeu.shopper.paypal.config.PaypalPaymentMethod;
import com.seeu.shopper.paypal.service.PaypalService;
import com.seeu.shopper.ship.service.ShipService;
import com.seeu.shopper.user.model.UserCoupon;
import com.seeu.shopper.user.service.UserAddressService;
import com.seeu.shopper.user.service.UserCouponService;
import com.seeu.shopper.user.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by neo on 30/08/2017.
 * <p>
 * 提供给用户下单使用
 */
@Controller
public class PlaceOrder2Controller {

    @Resource
    PriceAtStockV2Service priceAtStockV2Service;
    @Resource
    UserInfoService userInfoService;
    @Resource
    ShipService shipService;
    @Resource
    UnitService unitService;
    @Resource
    OrderCreaterService orderCreaterService;
    @Resource
    UserAddressService userAddressService;
    @Resource
    OrderBasicService orderBasicService;
    @Resource
    PaypalService paypalService;
    @Resource
    UserCouponService userCouponService;

    /**
     * 用户提交订单，直接下单并跳转到支付环境
     * <p>
     * 会清空购物车
     * <p>
     * 该请求会锁定库存信息，直接减少库存量，交易失败后会重新更新库存
     * <p>
     * 在该页面有如下功能：物流选择、地址选择、支付选择页面 或者提示登录页面、error页面
     *
     * @param uid
     * @param userType
     * @param productStr
     * @param coupon
     * @return
     */
    @RequestMapping("/placeorder")
    public String makeOrder(Model model,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestAttribute("uid") Integer uid,
                            @RequestAttribute("type") String userType,
                            @RequestParam(value = "coin", required = false) String coin,
                            @CookieValue(value = "products", required = false) String productStr,
                            @CookieValue(value = "coupon", required = false) String coupon,
                            @RequestParam("shipid") Integer shipid,
                            @RequestParam(value = "payment", required = false) Integer paymentID,
                            @RequestParam("address") Integer addressID,
                            @RequestParam(value = "note", required = false) String note) {

        if (uid == 0 || OAuthType.isVisitor(userType)) {
            // 提示登录，转到登录页面
            return "redirect:/signin";
        }

        if (productStr == null) {
            model.addAttribute("code", 400);
            model.addAttribute("message", "No Product Information.");
            return "shop/info";
        }

        // 验证商品信息
        productStr = productStr.replaceAll("x", ",").replaceAll("w", "\"").replaceAll("v", ":").replaceAll("\\(", "{").replaceAll("\\)", "}");
        productStr = productStr.replaceAll("norm", "normValues").replaceAll("qty", "quantity");

        try {
            ////////////////////// 格式化商品信息字符串 ///////////////////////////////////////////////////////
            JSONArray array = JSONArray.parseArray(productStr);
            if (array == null) {
                model.addAttribute("code", 400);
                model.addAttribute("message", "No Product Information.");
                return "shop/info";
            }
            ArrayList<ProductQuantityModelU> modelUs = new ArrayList<>();
            for (Object obj : array) {
                ProductQuantityModelU modelU = JSON.parseObject(obj.toString(), ProductQuantityModelU.class);
                Integer qty = modelU.getQuantity();
                if (qty < 1) {  // 不足一件，不计价
                    continue;
                }
                modelUs.add(modelU);
            }
            if (modelUs.size() == 0) {
                model.addAttribute("code", "Empty !");
                model.addAttribute("message", "You have no product in your cart.");
                return "shop/mycartempty";
            }

            ////////////////////// 计价器 ////////////////////////////////////////////////////////////////////
            // start cal price ////////////////////////////////////////////////
            PriceModel priceModel;
            if (coupon == null) coupon = "";
            int couponLength = coupon.trim().length();
            if (couponLength == 20) {
                // 密文优惠券计价
                priceModel = priceAtStockV2Service.calPrice(modelUs, coupon.trim(), uid);
            } else if (couponLength == 5) {
                // 5 位码计价
                priceModel = priceAtStockV2Service.calPriceCD5(modelUs, coupon, uid);
            } else {
                // 无优惠券
                priceModel = priceAtStockV2Service.calPrice(modelUs);
            }

            if (priceModel == null) {
                model.addAttribute("code", 400);
                model.addAttribute("message", "No Product Information.");
                return "shop/info";
            }
            if (priceModel.getStatus() == PriceModelStatus.NO_PRODUCT) {
                model.addAttribute("code", 400);
                model.addAttribute("message", "No Product Information.");
                response.addCookie(new Cookie("coupon", ""));
                return "shop/info";
            }
            if (priceModel.getStatus() == PriceModelStatus.COUPON_FAIL) {
                model.addAttribute("code", 400);
                model.addAttribute("message", "This coupon was useless.");
                response.addCookie(new Cookie("coupon", ""));
                return "shop/info";
            }
            if (priceModel.getStatus() == PriceModelStatus.COUPON_BEFORE_TIME || priceModel.getStatus() == PriceModelStatus.COUPON_OUT_TIME || priceModel.getStatus() == PriceModelStatus.COUPON_NOT_AVAILABLE) {
                model.addAttribute("code", 400);
                model.addAttribute("message", "This coupon was not in force.");
                response.addCookie(new Cookie("coupon", ""));
                return "shop/info";
            }
            if (priceModel.getStatus() == PriceModelStatus.NO_STOCKINFO) {
                model.addAttribute("code", 400);
                model.addAttribute("message", "No Stock Information.");
                response.addCookie(new Cookie("coupon", ""));
                return "shop/info";
            }

            ////////////////////// 单位换算 ////////////////////////////////////////////////////////////////////
            // 价格换算
            String unit = "US$";
            if (coin != null && !coin.equals("USD")) {
                unit = unitService.getUnit(coin);
                if (!unit.equals("US$")) {
                    priceModel.setWeightPrice(unitService.exchangeUnit(coin, priceModel.getWeightPrice()));
                    priceModel.setPrice(unitService.exchangeUnit(coin, priceModel.getPrice()));
                    priceModel.setOriginPrice(unitService.exchangeUnit(coin, priceModel.getOriginPrice()));
                    priceModel.setSalePrice(unitService.exchangeUnit(coin, priceModel.getSalePrice()));
                    priceModel.setSavePrice(unitService.exchangeUnit(coin, priceModel.getSavePrice()));
                    for (PriceProductModel pro : priceModel.getProducts()) {
                        pro.setSalePrice(unitService.exchangeUnit(coin, pro.getSalePrice()));
                        pro.setOriginPrice(unitService.exchangeUnit(coin, pro.getOriginPrice()));
                        pro.setPrice(unitService.exchangeUnit(coin, pro.getPrice()));
                    }
                }
            }


            // 规整用户数据信息
            if (note == null) {
                note = "";
            }
            if (paymentID == null) {
                paymentID = 1; // PayPal
            }

            ////////////////////// 订单创建（完整订单，包含物流、地址、支付方式信息及相关价格计算） //////////////////////////////////////////
            // 最后，下单（就算库存不足也会下单，但是用户会收到库存不足的信息，管理员可查看并解决）
            OrderBasic orderBasic = orderCreaterService.placeOrder(uid, unitService.isAvailable(coin) ? coin : "USD", priceModel, paymentID, shipid, addressID, note);
            if (orderBasic == null) {
                model.addAttribute("code", 404);
                model.addAttribute("message", "Address information or Ship method was not exist. Please recreate this order or choose another option.");
                return "shop/info";
            }
            // 验证库存信息（会直接锁定库存，减少库存量）
            boolean updateSuccess = orderCreaterService.stockUpdate(orderBasic.getOid());
            if (!updateSuccess) {
                model.addAttribute("code", 400);
                model.addAttribute("message", "Stock empty.");
                return "shop/info";
            }
            ////////////////////// 清空购物车 //////////////////////////////////////////
            response.addCookie(new Cookie("products", ""));// 清空购物车

            ////////////////////// 让 20 码优惠券失效 //////////////////////////////////////////
            if (priceModel.getStatus() == PriceModelStatus.SUCCESS_COUPON) {
                UserCoupon userCoupon = userCouponService.findBy("code", coupon);
                if (userCoupon != null) {
                    userCoupon.setStatus(1);// 被消费
                    userCouponService.update(userCoupon);
                } else {
                    // 创建新记录
                    UserCoupon userCoup = new UserCoupon();
                    userCoup.setCid(priceModel.getCid());
                    userCoup.setStatus(1);
                    userCoup.setCode(coupon);
                    userCoup.setUid(uid);
                    userCouponService.save(userCoup);
                }
            }
            ////////////////////// 让 5 码优惠券失效（该用户不再可以使用） //////////////////////////////////////////
            if (priceModel.getStatus() == PriceModelStatus.SUCCESS_COUPON_PUBLIC) {
                // 创建新记录
                UserCoupon userCoup = new UserCoupon();
                userCoup.setCid(priceModel.getCid());
                userCoup.setStatus(1);
                userCoup.setCode(coupon);
                userCoup.setUid(uid);
                userCouponService.save(userCoup);
            }


            ////////////////////// 支付 //////////////////////////////////////////
            // 订单创建成功，支付！
            String oid = orderBasic.getOid();
            paymentID = orderBasic.getPayMethod();
            BigDecimal price = orderBasic.getPrice().add(orderBasic.getShipPrice());// 商品价 + 物流价
            unit = orderBasic.getUnit();
            // 支付
            if (paymentID == 1) {

                // 更改支付状态
                OrderBasic orderBasic1 = new OrderBasic();
                orderBasic1.setOid(oid);
                orderBasic1.setStatus(OrderStatus.PAYING_PAYPAL); // PayPal 支付中
                orderBasicService.update(orderBasic1);

                // PayPal
//                String cancelUrl = URLUtils.getBaseURl(request) + "/paypal/cancel";
//                String successUrl = URLUtils.getBaseURl(request) + "/paypal/success";
                String cancelUrl = "https://leelbox-tech.com/paypal/cancel";
                String successUrl = "https://leelbox-tech.com/paypal/success";
                try {
                    Payment payment = paypalService.createPayment(
                            price.doubleValue(),
                            unit,
                            oid,
                            PaypalPaymentMethod.paypal,
                            PaypalPaymentIntent.sale,
                            "Leelbox-tech Products [ Order ID : " + oid + " ]",
                            cancelUrl,
                            successUrl);
                    for (Links links : payment.getLinks()) {
                        if (links.getRel().equals("approval_url")) {
                            return "redirect:" + links.getHref();
                        }
                    }
                } catch (PayPalRESTException e) {
                    model.addAttribute("code", "PayPal Exception 1503");
                    model.addAttribute("message", e.getMessage());
                    return "shop/info";
                }
                model.addAttribute("code", 400);
                model.addAttribute("message", "Payment Exception [ code = " + 400 + " ], Please try again.");
                return "shop/info";
            } else {
                // BankTransfer
                // 更改支付状态
                OrderBasic orderBasic2 = new OrderBasic();
                orderBasic2.setOid(oid);
                orderBasic2.setStatus(OrderStatus.PAYING_BANK_TRANSFER); // 转账支付中
                orderBasicService.update(orderBasic2);

                return "redirect:/banktransfer?oid=" + oid;
            }
            ////////////////////// 支付完成 //////////////////////////////////////////

        } catch (JSONException ex) {
            model.addAttribute("code", 400);
            model.addAttribute("message", "No Product Information.");
            return "shop/info";
        }
    }
}
