package com.seeu.shopper.ashop;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.seeu.shopper.order.iservice.OrderStatus;
import com.seeu.shopper.order.iservice.OrderStatusService;
import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.model.OrderProduct;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.order.service.OrderProductService;
import com.seeu.shopper.paypal.config.PaypalPaymentIntent;
import com.seeu.shopper.paypal.config.PaypalPaymentMethod;
import com.seeu.shopper.paypal.service.PaypalService;
import com.seeu.shopper.paypal.util.URLUtils;
import com.seeu.shopper.user.iservice.UserAmountService;
import com.seeu.shopper.user.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by neo on 09/09/2017.
 * <p>
 * 用户点击支付后，若是选择的 paypal 支付、BankTransfer 则跳转入该请求
 */
@Controller
public class PayController {

    @Resource
    PaypalService paypalService;
    @Resource
    OrderBasicService orderBasicService;
    @Resource
    OrderStatusService orderStatusService;
    @Resource
    UserAmountService userAmountService;

    @RequestMapping("/pay/{oid}")
    public String pay(Model model,
                      @PathVariable("oid") String oid,
                      HttpServletRequest request) {
        // 需要检验该订单信息是否完整（以是否含有 paymentID 为标示）
        if (oid == null || oid.length() != 18) {
            model.addAttribute("code", 404);
            model.addAttribute("message", "No such order information, you can checkout again to create a new order.");
            return "shop/info";
        }
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null) {
            model.addAttribute("code", 404);
            model.addAttribute("message", "No such order information, you can checkout again to create a new order.");
            return "shop/info";
        }
        // 如果已经支付
        if (orderStatusService.hasPaid(orderBasic.getStatus())) {
            model.addAttribute("code", 400);
            model.addAttribute("message", "The order has been paid and does not need to be paid repeatedly.");
            return "shop/info";
        }

        Integer paymentID = orderBasic.getPayMethod();
        BigDecimal price = orderBasic.getPrice().add(orderBasic.getShipPrice());// 商品价 + 物流价
        String unit = orderBasic.getUnit();
        // 支付
        if (paymentID == 1) {

            // 更改支付状态
            OrderBasic orderBasic1 = new OrderBasic();
            orderBasic1.setOid(oid);
            orderBasic1.setStatus(OrderStatus.PAYING_PAYPAL); // PayPal 支付中
            orderBasicService.update(orderBasic1);

            // PayPal
//            String cancelUrl = URLUtils.getBaseURl(request) + "/paypal/cancel";
//            String successUrl = URLUtils.getBaseURl(request) + "/paypal/success";
            String cancelUrl = "https://leelbox-tech.com/paypal/cancel";
            String successUrl = "https://leelbox-tech.com/paypal/success";
            try {
                Payment payment = paypalService.createPayment(
                        price.doubleValue(),
                        unit,
                        oid,
                        PaypalPaymentMethod.paypal,
                        PaypalPaymentIntent.sale,
                        "Leelbox-tech Products [ Order ID : " + oid + " ]",
                        cancelUrl,
                        successUrl);
                for (Links links : payment.getLinks()) {
                    if (links.getRel().equals("approval_url")) {
                        return "redirect:" + links.getHref();
                    }
                }
            } catch (PayPalRESTException e) {
                //
            }
            model.addAttribute("code", 400);
            model.addAttribute("message", "Payment Exception [ code = " + 400 + " ], Please try again.");
            return "shop/info";
        } else {
            // BankTransfer
            // 更改支付状态
            OrderBasic orderBasic2 = new OrderBasic();
            orderBasic2.setOid(oid);
            orderBasic2.setStatus(OrderStatus.PAYING_BANK_TRANSFER); // 转账支付中
            orderBasicService.update(orderBasic2);

            return "redirect:/banktransfer?oid=" + oid;
        }
    }

    ////////////////以下两个方法为 PayPal 回调函数/////////////////

    @RequestMapping("/paypal/cancel")
    public String cancelPay() {
        return "paypal/cancel";
    }


    @Resource
    OrderProductService orderProductService;

    @RequestMapping("/paypal/success")
    public String successPay(Model model,
                             @RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                Transaction transaction = payment.getTransactions().get(0);
                if (transaction == null) {
                    model.addAttribute("code", "PayPal Error");
                    model.addAttribute("message", "Do not repeat the payment, please contact the administrator to check your order status.");
                    return "shop/info";
                }
                String oid = transaction.getInvoiceNumber();
                OrderBasic orderBasic = new OrderBasic();
                orderBasic.setOid(oid);
                orderBasic.setStatus(OrderStatus.PAYED); // 支付成功，等待发货
                orderBasicService.update(orderBasic); // 更新订单状态

                OrderBasic basic = orderBasicService.findBy("oid", oid);

                // 用户消费额增加
                userAmountService.updateAmount(basic.getUid(), BigDecimal.valueOf(Double.parseDouble(transaction.getAmount().getTotal())));

                model.addAttribute("oid", oid);
                model.addAttribute("price", transaction.getAmount().getTotal());
                model.addAttribute("unit", transaction.getAmount().getCurrency());

                Condition condition = new Condition(OrderProduct.class);
                condition.createCriteria().andCondition("oid = " + oid);
                List<OrderProduct> productList = orderProductService.findByCondition(condition);
                model.addAttribute("products", productList);
                model.addAttribute("basic", basic);
                return "paypal/success";
            }
        } catch (PayPalRESTException e) {
//            log.error(e.getMessage());
            model.addAttribute("code", "PayPal Error");
            model.addAttribute("message", e.getMessage());
            return "shop/info";
        }
        return "redirect:/";    // 其余情况则回到主页
    }

}
