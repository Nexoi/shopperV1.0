//package com.seeu.shopper.adminorder;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.seeu.core.Result;
//import com.seeu.core.ResultGenerator;
//import com.seeu.shopper.adminorder.model.PriceModel;
//import com.seeu.shopper.adminorder.model.ProductQuantityModelU;
//import com.seeu.shopper.order.iservice.PriceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//
///**
// * 注销，弃用
// * Created by neo on 29/08/2017.
// * <p>
// * 计价器API
// */
//@RestController
//@RequestMapping("/api/admin/v1/order/price")
//public class ApiPagePriceController {
//
//    @Autowired
//    PriceService priceService;
//
//    @PostMapping
//    public Result calPrice(@RequestParam("products") String productStr, @RequestParam(value = "coupon", required = false) String coupon) {
//        JSONArray array = JSONArray.parseArray(productStr);
//        if (array == null)
//            return ResultGenerator.genFailResult("Parameter 'products' is null.");
//
//        ArrayList<ProductQuantityModelU> modelUs = new ArrayList<>();
//        for (Object obj : array) {
//            ProductQuantityModelU modelU = JSON.parseObject(obj.toString(), ProductQuantityModelU.class);
//            modelUs.add(modelU);
//        }
//        PriceModel priceModel;
//        if (coupon == null || coupon.trim().length() != 20) {
//            // 无优惠券
//            priceModel = priceService.calPrice(modelUs);
//        } else {
//            // 优惠券计价
//            priceModel = priceService.calPrice(modelUs, coupon.trim());
//        }
//        return ResultGenerator.genSuccessResult(priceModel);
//    }
//
//    private static final String PRODUCT_NAME = "L_PAYMENTREQUEST_0_NAMEm";
//    private static final String PRODUCT_AMT = "L_PAYMENTREQUEST_0_AMTm";
//    private static final String PRODUCT_QTY = "L_PAYMENTREQUEST_0_QTYm";
//
//}
