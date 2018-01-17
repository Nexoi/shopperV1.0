package com.seeu.shopper.ashop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seeu.shopper.adminorder.model.PriceModel;
import com.seeu.shopper.adminorder.model.PriceModelStatus;
import com.seeu.shopper.adminorder.model.PriceProductModel;
import com.seeu.shopper.adminorder.model.ProductQuantityModelU;
import com.seeu.shopper.ashop.service.UnitService;
import com.seeu.shopper.order.iservice.PriceAtStockV2Service;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 31/08/2017.
 * 用户点击"购物车"后计价、优惠券计价
 * <p>
 * 伟大的规则：如果规格信息木有或者为 0 ，则选取规格列表第一个可用的信息给前台
 */
@Controller
public class CartController {

    @Resource
    PriceAtStockV2Service priceAtStockV2Service;// mycart 专用计价器，无规格信息会采取规格表第一个有效记录进行计算
    @Resource
    UnitService unitService;

    @RequestMapping("/mycart")
    public String checkMyCart(Model model,
//                              HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestAttribute(value = "uid", required = false) Integer uid,
//                              @RequestAttribute(value = "type",required = false) String userType,
                              @RequestParam(value = "coin", required = false) String coin,
                              @CookieValue(value = "products", required = false) String productStr,
                              @CookieValue(value = "coupon", required = false) String coupon) {
        // 规整化 str 为 json
        if (productStr == null) {
            model.addAttribute("code", "Empty !");
            model.addAttribute("message", "You have no product in your cart.");
            return "shop/mycartempty";
        }
        productStr = productStr.replaceAll("x", ",").replaceAll("w", "\"").replaceAll("v", ":").replaceAll("\\(", "{").replaceAll("\\)", "}");
        productStr = productStr.replaceAll("norm", "normValues").replaceAll("qty", "quantity");

        // 验证订单是否可行
        JSONArray array = JSONArray.parseArray(productStr);
        if (array == null) {
            model.addAttribute("code", "Empty !");
            model.addAttribute("message", "You have no product in your cart.");
            return "shop/mycartempty";
        }
        ArrayList<ProductQuantityModelU> modelUs = new ArrayList<>();
        for (Object obj : array) {
            ProductQuantityModelU modelU = JSON.parseObject(obj.toString(), ProductQuantityModelU.class);
            Integer qty = modelU.getQuantity();
            if (qty < 1) {// 不足一件，不计价
                continue;
            }
            modelUs.add(modelU);
        }
        //
        if (modelUs.size() == 0) {
            model.addAttribute("code", "Empty !");
            model.addAttribute("message", "You have no product in your cart.");
            return "shop/mycartempty";
        }


        // start cal price ////////////////////////////////////////////////
        if (uid == null) uid = 0;
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
            response.addCookie(new Cookie("coupon", ""));
            return "shop/mycartempty";
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

        model.addAttribute("coupon", coupon);
        model.addAttribute("unit", unit);
        model.addAttribute("model", priceModel);
        return "shop/mycart";
    }
}
