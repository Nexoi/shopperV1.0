//package com.seeu.shopper.ashop;
//
//import com.seeu.oauth.OAuthType;
//import com.seeu.shopper.ashop.service.NormFormService;
//import com.seeu.shopper.order.iservice.OrderCreaterService;
//import com.seeu.shopper.order.iservice.PriceService;
//import com.seeu.shopper.order.model.OrderBasic;
//import com.seeu.shopper.order.model.OrderProduct;
//import com.seeu.shopper.order.model.OrderReceiver;
//import com.seeu.shopper.order.service.OrderBasicService;
//import com.seeu.shopper.order.service.OrderProductService;
//import com.seeu.shopper.order.service.OrderReceiverService;
//import com.seeu.shopper.ship.service.ShipService;
//import com.seeu.shopper.user.service.UserInfoService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestAttribute;
//import org.springframework.web.bind.annotation.RequestParam;
//import tk.mybatis.mapper.entity.Condition;
//
//import javax.annotation.Resource;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.Map;
//
///**
// * [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
// * 该类已被注销，请勿再使用，PlaceOrder2Controller 已经实现并替代了该类功能
// * [][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]
// * Created by neo on 01/09/2017.
// */
//@Controller
//public class PlaceOrderController {
//
//
//    @Resource
//    PriceService priceService;
//    @Resource
//    OrderCreaterService orderCreaterService;
//    @Resource
//    UserInfoService userInfoService;
//    @Resource
//    ShipService shipService;
//
//    @Resource
//    OrderBasicService orderBasicService;
//    @Resource
//    OrderProductService orderProductService;
//    @Resource
//    OrderReceiverService orderReceiverService;
//    @Resource
//    NormFormService normFormService;
//
//    /**
//     * <p>
//     * <p>
//     * 下单，生成订单，必须是要登录用户
//     * <p>
//     * 不会再计价，所以不需要再确认计价单位
//     * <p>
//     * 会清空购物车
//     * <p>
//     * 该请求会锁定库存信息，直接减少库存量，交易失败后会重新更新库存
//     * <p>
//     * 用户点击该页的 PayNow 按钮后跳转到支付页面（PayPal、BankTransfer）
//     *
//     * @param model
//     * @param uid
//     * @param userType
//     * @return
//     */
////    TODO 该请求已被注销，请替换为 PlaceOrder2Controller 内对应方法
////    @RequestMapping("/placeorder")
//    public String placeOrder(Model model,
//                             HttpServletResponse response,
//                             @RequestAttribute("uid") Integer uid,
//                             @RequestAttribute("type") String userType,
//                             @RequestParam("oid") String oid,
//                             @RequestParam("shipid") Integer shipid,
//                             @RequestParam("payment") Integer paymentID,
//                             @RequestParam("address") Integer addressID,
//                             @RequestParam(value = "note", required = false) String note) {
//
//        if (uid == 0 || OAuthType.isVisitor(userType)) {
//            // 提示登录，转到登录页面
//            return "redirect:/signin";
//        }
//        //
//        // 验证订单是否存在
//        if (oid == null || oid.length() != 18) {
//            // 订单必定不存在
//            model.addAttribute("code", 404);
//            model.addAttribute("message", "No such order information, you can checkout again to create a new order.");
//            return "shop/info";
//        }
//
//        // 规整数据信息
//        if (note == null) {
//            note = "";
//        }
//        if (paymentID == null) {
//            paymentID = 1; // PayPal
//        }
//        // 验证库存信息（会直接锁定库存，减少库存量）
//        boolean updateSuccess = orderCreaterService.stockUpdate(oid);
//        if (!updateSuccess) {
//            model.addAttribute("code", 400);
//            model.addAttribute("message", "Stock empty.");
//            return "shop/info";
//        }
//        // 绑定订单信息（完善订单信息，使其状态为：支付中）
//        boolean isBindSuccess = orderCreaterService.bindOrderInfo(oid, paymentID, shipid, addressID, note);
//        if (!isBindSuccess) {
//            // 绑定订单信息失败，可能：1. 无此订单信息；2. 无物流信息
//            model.addAttribute("code", 404);
//            model.addAttribute("message", "Order information or Ship method was not exist. Please recreate this order or choose another option.");
//            return "shop/info";
//        }
//
//        // 加载订单信息
//        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);    // 基础信息
//
//        OrderReceiver receiver = orderReceiverService.findBy("oid", oid);    // 物流地址信息
//
//        Condition condition = new Condition(OrderProduct.class);
//        condition.createCriteria().andCondition("oid = " + oid);
//        List<OrderProduct> products = orderProductService.findByCondition(condition);   // 商品信息
//        // 规整化商品信息（将规格信息具体显示）
//        for (OrderProduct product : products) {
//            Map<Integer, String> norms = normFormService.getNormName(product.getNorms());
//            // 规整化为：[Color] [Size] 这样
//            String normNames = "";
//            for (String value : norms.values()) {
//                normNames += " [" + value + "]";
//            }
//            product.setNorms(normNames);
//        }
//        String unit = orderBasic.getUnit();
//
//
//        response.addCookie(new Cookie("products", ""));// 清空购物车
//        model.addAttribute("unit", unit);
//        model.addAttribute("oid", oid);
//        model.addAttribute("order", orderBasic);
//        model.addAttribute("products", products);
//        model.addAttribute("receiver", receiver);
//        return "shop/placeorder";
//    }
//}
