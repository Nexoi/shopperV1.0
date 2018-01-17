//package com.seeu.shopper.ashop.domain;
//
//import com.seeu.shopper.product.model.ProductNorm;
//import com.seeu.shopper.product.service.ProductNormService;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by neo on 08/09/2017.
// */
//public class FullNormModel {
//    public static Map<Integer, String> norms;
//
//    public static Map getNormItem(ProductNormService service) {
//        List<ProductNorm> norms = productNormService.findByIds(normIDs.replaceAll("_", ","));
//        HashMap<Integer, String> map = new HashMap<>();
//        for (ProductNorm norm : norms) {
//            map.put(norm.getId(), norm.getNormName());
//        }
//        return map;
//        return norms;
//    }
//
//    public static void flushNorm() {
//        norms = null;
//    }
//}
