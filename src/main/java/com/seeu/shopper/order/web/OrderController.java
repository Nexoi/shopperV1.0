//package com.seeu.shopper.order.web;
//
//import com.seeu.core.Result;
//import com.seeu.core.ResultGenerator;
//import com.seeu.shopper.coupon.domain.Coupon;
//import com.seeu.shopper.coupon.service.CouponService;
//import com.seeu.shopper.order.domain.*;
//import com.seeu.shopper.order.service.OrderBasicService;
//import com.seeu.shopper.order.service.OrderReceiverService;
//import com.seeu.shopper.order.service.OrderService;
//import com.seeu.shopper.order.service.impl.OrderServiceImpl;
//import com.seeu.shopper.product.domain.Product;
//import com.seeu.shopper.product.domain.ProductCoupon;
//import com.seeu.shopper.product.service.ProductCouponService;
//import com.seeu.shopper.product.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import tk.mybatis.mapper.entity.Condition;
//
//import java.math.BigDecimal;
//import java.util.*;
//
///**
// * Created by neo on 10/08/2017.
// * <p>
// * 订单管理（用户操作端）【创建订单】
// * <p>
// * 普通／优惠券订单：
// * 优惠券一次订单只能使用一张，满足条件即可使用，下单成功则消费优惠券
// * <p>
// * 活动订单：
// * 不可使用优惠券，订单成功即
// */
//@EnableTransactionManagement
//@RestController
//@RequestMapping("/api/shop/v1/order")
//public class OrderController {
//    @Autowired
//    OrderService orderService;
//    @Autowired
//    OrderBasicService orderBasicService;
//    @Autowired
//    ProductService productService;
//    @Autowired
//    CouponService couponService;
//    @Autowired
//    ProductCouponService productCouponService;
//    @Autowired
//    OrderReceiverService orderReceiverService;
//
//    /**
//     * 如果用户为游客，则写入 uid 为 -1 表示后续(登录后？)可更改 uid，否则写死 uid 不可更改
//     *
//     * @return
//     */
//    @PostMapping
//    @Transactional
//    public Result postOrder(Order order, @RequestAttribute("uid") Integer uid, @RequestAttribute("name") String username) {
//        // 检验信息
//        // 收货人信息
//        OrderReceiver receiver = order.getReceiver();
//        if (!orderService.checkReceiverData(receiver)) {
//            return ResultGenerator.genFailResult("Recipient information is incomplete!");
//        }
//
//        // 优惠券信息
//        String couponCODE = order.getCoupon();
//        int coupon_status = -1; // 优惠券状态
//        BigDecimal start_price = null;
//        BigDecimal disprice = null;
//        BigDecimal discount;
//        List<Integer> coupon_pids = null;
//
//        if (couponCODE != null && couponCODE.trim().length() == 20) {
//            // 翻译优惠券信息
//            String couponID = orderService.decode(couponCODE);
//            if (couponID != null) {
//                // 检查是否可用，过期
//                Coupon couponModel = couponService.findBy("cid", couponID);
//                if (couponModel != null || couponModel.getIsAvailable()) {
//                    // 优惠券信息可用
//                    long now = new Date().getTime();
//                    long start = couponModel.getStartTime().getTime();
//                    long end = couponModel.getEndTime().getTime();
//                    if (start < now && now < end) {
//                        // 优惠券在使用期间内
//                        start_price = couponModel.getStartPrice();
//                        if (couponModel.getType() == 1) {
//                            // 满减
//                            coupon_status = 1;
//                            disprice = couponModel.getDisprice();
//                        } else if (couponModel.getType() == 2) {
//                            // 打折
//                            coupon_status = 2;
//                            discount = couponModel.getDiscount();
//                        }
//                        // 匹配可用商品信息列表
//                        coupon_pids = new ArrayList<>();
//                        Condition condition = new Condition(ProductCoupon.class);
//                        condition.createCriteria().andCondition("cid = '" + couponID + "' ");
//                        List<ProductCoupon> list = productCouponService.findByCondition(condition);
//                        for (ProductCoupon productCoupon : list) {
//                            coupon_pids.add(productCoupon.getPid());
//                        }
//                    }
//                }
//                // 检查是否匹配该商品
//            }
//        }
//
//
//        // 计算价格
//        List<ProductAmount> products = order.getProducts();
//        PriceModel finalPrice = null;
//        if (coupon_status == 1) {
//            // 满减
//            finalPrice = orderService.calPriceCouponDisprice(products, start_price, disprice, coupon_pids);
//        } else if (coupon_status == 2) {
//            // 打折
//            finalPrice = orderService.calPriceCouponDiscount(products, start_price, discount, coupon_pids);
//        } else {
//            // 普通
//            finalPrice = orderService.calPriceNormal(products);
//        }
//
//        if (finalPrice == null) {
//            // 价格计算失败，打折信息有误？优惠券信息有误？
//            return ResultGenerator.genFailResult("Products information error");
//        }
//
//        // 创建订单
//        // 基础信息
//        String OID = orderService.genOrderID();
//        OrderBasic orderBasic = new OrderBasic();
//        orderBasic.setOid(OID);
//        orderBasic.setShipId(order.getShip());
//        orderBasic.setCurrentPrice(finalPrice.getCurrentPrice().setScale(2, BigDecimal.ROUND_UP));// 进位处理
//        orderBasic.setTotalPrice(finalPrice.getTotalPrice().setScale(2, BigDecimal.ROUND_UP));
//        orderBasic.setUid(uid);
//        orderBasic.setUserName(username);
//        orderBasic.setStatus(OrderStatus.CARTING);  // 设定状态为计价成功，持久化数据成功，但下单
//        orderBasic.setUpdateTime(new Date());
//        orderBasic.setCreateTime(new Date());
//        orderBasicService.save(orderBasic);
//        // 记录商品信息
//        orderService.saveProducts(OID, products, coupon_status == -1);
//        // 记录用户地址信息
//        receiver.setOid(OID);
//        orderReceiverService.save(receiver);
//
//        // 返回信息
//        Map<String, Object> map = new HashMap<>();
//        map.put("oid", OID);
//        map.put("uid", uid);
//        map.put("totalPrice", orderBasic.getTotalPrice());
//        map.put("currentPrice", orderBasic.getCurrentPrice());
//        map.put("status", orderBasic.getStatus());
//        map.put("createTime", orderBasic.getCreateTime());
//        if (coupon_status != -1) {
//            map.put("coupon", couponCODE);
//        }
//        return ResultGenerator.genSuccessResult(map);
//    }
//
//    /**
//     * 搞活动特殊计价模式
//     *
//     * @return
//     */
//    @PostMapping("/activity")
//    public Result postOrderActivity(OrderActivity order) {
//        // 检验信息
//        // 收货人信息
//        OrderReceiver receiver = order.getReceiver();
//        if (!orderService.checkReceiverData(receiver)) {
//            return ResultGenerator.genFailResult("Recipient information is incomplete!");
//        }
//
//        // 计算价格
//        List<ProductAmount> products = order.getProducts();
//
//        return null;
//    }
//
//}
