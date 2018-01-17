package com.seeu.shopper.order.iservice;

/**
 * Created by neo on 11/08/2017.
 */
public class OrderStatus {
    public static final Integer FORCE_CANCEL = -2;   // 强制取消订单，交易关闭

    public static final Integer CANCEL = -1;   // 取消订单，交易关闭
    public static final Integer UNPAY = 1;  // 已下单，未支付
    public static final Integer PAYING = 2;  // 支付中，等待付款
    public static final Integer PAYED = 3;   // 支付成功，等待发货
    public static final Integer SHIPING = 4;   // 已发货，物流中
    public static final Integer SHIPEND_FINISHED = 5; // 用户确认收货，交易关闭

    public static final Integer AUTO_CANCEL_2 = 201;  // 超过七天未支付已自动取消订单，交易关闭
    public static final Integer PAYING_BANK_TRANSFER = 211;  // 转账支付中
    public static final Integer PAYING_PAYPAL = 212;  // PayPal支付中
    public static final Integer PAYING_CREIDT = 213;  // 信用卡支付中

    public static final Integer DROP_MONEY_REQUEST = 301;  // 退款申请
    public static final Integer DROP_MONEY_ARGEE = 302;  // 同意退款，正在退款中
    public static final Integer DROP_MONEY_REFUSE = 321;  // 拒绝退款，即将发货

    public static final Integer DROP_PRODUCT_REQUEST = 400; // 申请退货
    public static final Integer DROP_PRODUCT_ARGEE = 401;   // 同意退货，等待用户寄送回货物
    public static final Integer DROP_PRODUCT_DROP_MONEY_ING = 402;   // 收到货物，正在退款
    public static final Integer DROP_PRODUCT_AUTO_CANCEL = 403;   // 未收到货物（商家）（在规定天数以内），自动确认收货（用户）
    public static final Integer DROP_PRODUCT_DIS_ARGEE = 421;   // 不同意退货，货物已在物流中
    public static final Integer DROP_PRODUCT_SUCCESS = 4021;   // 退货退款成功，交易关闭

    public static final Integer FINISH = 0;   // 交易关闭
}
