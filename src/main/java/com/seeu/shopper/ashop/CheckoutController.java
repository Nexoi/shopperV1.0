package com.seeu.shopper.ashop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.seeu.oauth.OAuthType;
import com.seeu.shopper.adminorder.model.PriceModel;
import com.seeu.shopper.adminorder.model.PriceModelStatus;
import com.seeu.shopper.adminorder.model.PriceProductModel;
import com.seeu.shopper.adminorder.model.ProductQuantityModelU;
import com.seeu.shopper.ashop.service.UnitService;
import com.seeu.shopper.order.iservice.OrderCreaterService;
import com.seeu.shopper.order.iservice.PriceAtStockV2Service;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.ship.model.Ship;
import com.seeu.shopper.ship.service.ShipService;
import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.service.UserAddressService;
import com.seeu.shopper.user.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 30/08/2017.
 * <p>
 * 提供给用户下单使用
 */
@Controller
public class CheckoutController {

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

    /**
     * 用户提交订单，不下单
     * <p>
     * <p>
     * 在该页面有如下功能：物流选择、地址选择、支付选择页面 或者提示登录页面、error页面
     *
     * @param uid
     * @param userType
     * @param productStr
     * @param coupon
     * @return
     */
    @RequestMapping("/checkout")
    public String makeOrder(Model model,
                            HttpServletResponse response,
                            @RequestAttribute("uid") Integer uid,
                            @RequestAttribute("type") String userType,
                            @RequestParam(value = "coin", required = false) String coin,
                            @CookieValue("products") String productStr,
                            @CookieValue(value = "coupon", required = false) String coupon) {

        if (uid == 0 || OAuthType.isVisitor(userType)) {
            // 提示登录，转到登录页面
            return "redirect:/signin";
        }

        // 验证商品信息
        productStr = productStr.replaceAll("x", ",").replaceAll("w", "\"").replaceAll("v", ":").replaceAll("\\(", "{").replaceAll("\\)", "}");
        productStr = productStr.replaceAll("norm", "normValues").replaceAll("qty", "quantity");

        try {
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

            ////////////////////// 计价器 //////////////////////////////////////////
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
            }else{
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

            ////////////////////// 物流 //////////////////////////////////////////
            BigDecimal weight = priceModel.getWeight(); // 按 克 计算
            List<Ship> ships = shipService.findAll();
            for (Ship ship : ships) {
                // 计价
                if (weight.compareTo(BigDecimal.valueOf(1000)) != 1) { // 低于或等于 1KG
//                    ship.getBasePrice();
                    ship.setAddiPrice(BigDecimal.valueOf(0));
                } else {
                    int weight_ = weight.intValue();
                    weight_ /= 1000; // 换算成千克
                    ship.setAddiPrice(ship.getAddiPrice().multiply(BigDecimal.valueOf(weight_)));
                }
            }
            // 此时物流价格为：baseprice + addiprice


            ////////////////////// 单位换算 //////////////////////////////////////////
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
                    for (Ship ship : ships) {
                        ship.setBasePrice(unitService.exchangeUnit(coin, ship.getBasePrice()));
                        ship.setAddiPrice(unitService.exchangeUnit(coin, ship.getAddiPrice()));
                    }
                }
            }
            ////////////////////// 优惠券 //////////////////////////////////////////
            coupon = priceModel.getCoupon();
            if (coupon == null) coupon = "";
            // 重组 COUPON json
            List<PriceProductModel> priceProductModels = priceModel.getProducts();
            JSONArray ja = new JSONArray();
            for (PriceProductModel priceProductModel : priceProductModels) {
                JSONObject jo = new JSONObject();
                jo.put("pid", priceProductModel.getPid());
                jo.put("norm", priceProductModel.getNormValues());
                jo.put("qty", priceProductModel.getQuantity());
                ja.add(jo);
            }
            String json = ja.toJSONString();
            json = json.replaceAll(",", "x").replaceAll("\"", "w").replaceAll(":", "v").replaceAll("\\{", "\\(").replaceAll("\\}", "\\)");
            response.addCookie(new Cookie("products", json));
            // End form COUPON JSON
            ////////////////////// 优惠券 End //////////////////////////////////////////

            ////////////////////// 用户地址信息 //////////////////////////////////////////
            // 注入该用户地址信息
            Condition condition = new Condition(UserAddress.class);
            condition.createCriteria().andCondition("uid = " + uid);
            List<UserAddress> userAddresses = userAddressService.findByCondition(condition);

            model.addAttribute("address", userAddresses);
            model.addAttribute("coupon", coupon);       // 优惠券信息（可以不加入？）
            model.addAttribute("unit", unit);
            model.addAttribute("ships", ships);         // 物流信息（已经计算过价格）
            model.addAttribute("model", priceModel);   // 这个总价不包含物流价格，前端可以自行处理最终价格计算

        } catch (JSONException ex) {
            model.addAttribute("code", 400);
            model.addAttribute("message", "No Product Information.");
            return "shop/info";
        }
        return "shop/checkout";
    }

}
