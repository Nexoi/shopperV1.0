//package com.seeu.shopper.order.web;
//
//import com.seeu.shopper.order.model.OrderBasic;
//import com.seeu.shopper.order.iservice.OrderStatus;
//import com.seeu.shopper.order.service.OrderBasicService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by neo on 10/08/2017.
// * <p>
// * 支付
// */
//@EnableTransactionManagement
//@RestController
//@RequestMapping("/api/shop/v1/pay")
//public class PayController {
//    @Autowired
//    OrderBasicService orderBasicService;
//
//    /**
//     * 确定下单，跳转到选择支付方式页面
//     * @return
//     */
//    @RequestMapping("placeorder")// TODO
//    public String makeOrder(@RequestParam("oid") String oid) {
//        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
//        if (orderBasic == null) {
////            return "redirect:/No such order information, please check your cart first!";
//        }
//        // change order status
//        orderBasic.setStatus(OrderStatus.UNPAY);
//        // redict to pay choice page
//        // 跳转到支付选择界面，用户选择合适的方式进行支付，并记录支付方式
//        // ~
////        return "redirect:/Create order success! Please according to the guide to pay.";
//        return null;
//    }
//
//    /**
//     * 选择一种支付进入此页面，再进行跳转
//     *
//     * @param payType
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("pay")// TODO
//    public String pay(@RequestParam("paytype") Integer payType, @RequestParam("price") Double price) {
//        switch (payType) {
//            case 1:
//                // BANK~
//                return "redirect:/bankinfopage";
//            case 2:
//                // PayPal~
//                // 调取支付 API 信息，重定向进行支付
//                return "redirect:/paypelAPI" + price;
//            case 3:
//                // 货到付款~
//                return "redirect:/paypelAPI";
//            default:
//                return "redirect:/toPage : Please check your order information and request later.";
//        }
//    }
//
//    /**
//     * Paypal 支付回调
//     * @return
//     */
//    @RequestMapping("paypelback")
//    public String paypelCallBack(){
//        return null;
//    }
//
//}
