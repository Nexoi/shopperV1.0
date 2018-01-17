//package com.seeu.shopper.order.service;
//
//import com.seeu.core.Service;
//import com.seeu.shopper.order.domain.Order;
//import com.seeu.shopper.order.domain.OrderReceiver;
//import com.seeu.shopper.order.domain.PriceModel;
//import com.seeu.shopper.order.domain.ProductAmount;
//
//import java.math.BigDecimal;
//import java.util.List;
//
///**
// * Created by neo on 11/08/2017.
// */
//public interface OrderService {
//    public String genOrderID();
//
//    public String decode(String CDKEY);
//
//    public boolean checkReceiverData(OrderReceiver receiver);
//
//    /* 普通计价器 */
//    public PriceModel calPriceNormal(List<ProductAmount> products);
//
//    /* 优惠券计价器，打折 */
//    public PriceModel calPriceCouponDiscount(List<ProductAmount> products, BigDecimal startPrice, Integer discount, List<Integer> providePIDs);
//
//    /* 优惠券计价器，满减 */
//    public PriceModel calPriceCouponDisprice(List<ProductAmount> products, BigDecimal startPrice, BigDecimal disprice, List<Integer> providePIDs);
//
//    public void saveProducts(String OID, List<ProductAmount> products, boolean isUseCoupon);
//
//    /* 活动计价器 */
//    public PriceModel calPriceActivity(List<ProductAmount> products, List<Integer> providePIDs);
//
//}
