package com.seeu.shopper.adminorder.model;

/**
 * Created by neo on 29/08/2017.
 */
public class PriceModelStatus {
    public static final Integer SUCCESS = 1; // 计价成功
    public static final Integer SUCCESS_COUPON = 8; // 计价成功，优惠券被消费
    public static final Integer SUCCESS_COUPON_PUBLIC = 9; // 计价成功，优惠券被消费 5 位公开码
    public static final Integer FAILURE = -1; // 计价失败
    public static final Integer NO_PRODUCT = 2; // 计价失败，无此商品信息
    public static final Integer NO_STOCKINFO = 3; // 计价失败，无库存（规格）信息（不是库存不够，是没有这个规格信息）
    public static final Integer COUPON_FAIL = 4; // 计价失败，优惠券信息有误
    public static final Integer COUPON_BEFORE_TIME = 5; // 计价失败，未到优惠券使用期间
    public static final Integer COUPON_OUT_TIME = 6; // 计价失败，优惠券过期
    public static final Integer COUPON_NOT_AVAILABLE = 7; // 计价失败，优惠券不可使用，未开启

}
