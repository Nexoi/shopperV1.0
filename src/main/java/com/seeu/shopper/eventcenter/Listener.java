package com.seeu.shopper.eventcenter;

import com.seeu.shopper.config.iservice.FullConfigModel;
import com.seeu.shopper.config.service.ConfigService;
import com.seeu.shopper.coupon.iservice.ICouponService;
import com.seeu.shopper.mail.service.EmailSendUtilService;
import com.seeu.shopper.order.model.OrderReceiver;
import com.seeu.shopper.order.model.OrderShip;
import com.seeu.shopper.order.service.OrderReceiverService;
import com.seeu.shopper.order.service.OrderShipService;
import com.seeu.shopper.subscrible.model.Subscrible;
import com.seeu.shopper.subscrible.service.SubscribleService;
import com.seeu.shopper.user.model.UserLogin;
import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 05/09/2017.
 * <p>
 * 所有的通知事件都从这里走过
 */
@Component
public class Listener {
    Logger logger = Logger.getLogger(Listener.class);

    @Resource
    EmailSendUtilService emailSendUtilService;

    @EventListener
    public void login(UserLoginEvent userLoginEvent) {
        //获取登录用户对象
        UserLogin user = userLoginEvent.getUser();

        //../省略逻辑

        //输出注册用户信息
//        System.out.println("@EventListener登录信息，用户 Email：" + user.getEmail() + "，密码：" + user.getPassword());

        // 发送邮件？
    }

    @Resource
    ConfigService configService;
    @Resource
    ICouponService iCouponService;

    @EventListener
    public void register(UserRegistEvent userRegistEvent) {
        //获取注册用户对象
        UserLogin user = userRegistEvent.getUser();

        // 生成优惠券
        Map<String, String> config = FullConfigModel.getConfig(configService);
        String coupon = config.get("regist_coupon_id");
        List<String> coupons = iCouponService.createCoupons(coupon, 1);
        if (coupons == null || coupons.size() == 0) {
            return;// 不发了
        }
        // 发送邮件？
        String title = "Receive Your Coupons From Leelbox Now!";
        String url = "https://leelbox-tech.com/activecoupon/" + coupons.get(0);
        String text = "<div>" +
                "<div style='background-color:#ffffff;font-family:sans-serif;font-size:14px;line-height:1.4;margin:0;padding:0'>" +
                "<div style='color: #fff !important;background-color: rgb(42, 141, 197); padding: 20px !important;'>" +
                "<h2 style='margin-top: 40px;'>" +
                "Leelbox-tech" +
                "</h2>" +
                "<h4>" +
                "Click the link below to activate your coupon" +
                "</h4>" +
                "<br/>" +
                "</div>" +
                "<table border='0' cellpadding='0' cellspacing='0'" +
                "style='border-collapse:separate;background-color:#ffffff;width:100%'>" +
                "<tbody>" +
                "<tr>" +
                "<td style='font-family:sans-serif;font-size:14px;vertical-align:top'>&nbsp;</td>" +
                "<td style='font-family:sans-serif;font-size:14px;vertical-align:top;display:block;max-width:580px;padding:10px;width:580px;Margin:0 auto!important'>" +
                "<div style='box-sizing:border-box;display:block;Margin:0 auto;max-width:580px;padding:10px'>" +
                "<span style='color:transparent;display:none;height:0;max-height:0;max-width:0;opacity:0;overflow:hidden;width:0'>This is preheader text. Some clients will show this text as a preview.</span>" +
                "<table style='border-collapse:separate;background:#fff;border-radius:3px;width:100%'>" +
                "<tbody>" +
                "<tr>" +
                "<td style='font-family:sans-serif;font-size:14px;vertical-align:top;box-sizing:border-box;padding:20px'>" +
                "<table border='0' cellpadding='0' cellspacing='0'" +
                "style='border-collapse:separate;width:100%'>" +
                "<tbody>" +
                "<tr><td style='font-family:sans-serif;font-size:14px;vertical-align:top'>" +
                "<p style='font-family:sans-serif;font-size:24px;font-weight:bold;margin:0;Margin-bottom:30px;letter-spacing:1.5px'>" +
                "Welcome, " + userRegistEvent.getUserName() + "!</p>" +
                "<p style='font-family:sans-serif;font-size:14px;font-weight:normal;margin:0;Margin-bottom:15px'>" +
                "This is a message to receive your coupons." +
                "</p>" +
                "<p style='font-family:sans-serif;font-size:14px;font-weight:normal;margin:0;Margin-bottom:15px'>" +
                "After active coupon code [ " + coupons.get(0) + " ], " +
                "You will be able to use the lower price to buy high-quality goods on our website.</p>" +
                "<table border='0' cellpadding='0' cellspacing='0'" +
                "style='border-collapse:separate;box-sizing:border-box;width:100%'>" +
                "<tbody>" +
                "<tr>" +
                "<td align='left'" +
                "style='font-family:sans-serif;font-size:14px;vertical-align:top;padding-bottom:15px'>" +
                "<table border='0' cellpadding='0' cellspacing='0'" +
                "style='border-collapse:separate;width:100%;width:auto'>" +
                "<tbody>" +
                "<tr>" +
                "<td style='font-family:sans-serif;font-size:14px;vertical-align:top;background-color:#ffffff;border-radius:5px;text-align:center'>" +
                "<a href='" + url + "'" +
                "style='text-decoration:underline;background-color:#ffffff;border-radius:5px;box-sizing:border-box;color:#3498db;display:inline-block;font-size:14px;font-weight:bold;margin:30px 0;padding:12px 25px;text-decoration:none;text-transform:capitalize;background-image:linear-gradient(-62deg,rgb(42, 121, 197) 0%,rgb(42, 141, 197) 100%);color:#ffffff'" +
                "target='_blank'>" +
                "Receive Coupon" +
                "</a></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table><div style='clear:both;padding-top:10px;text-align:center;width:100%'>" +
                "<table border='0' cellpadding='0' cellspacing='0'" +
                "style='border-collapse:separate;width:100%'>" +
                "<tbody>" +
                "<tr>" +
                "<td style='font-family:sans-serif;font-size:14px;vertical-align:top;color:#999999;font-size:12px;text-align:center'>" +
                "<p style='color:#999999;font-size:12px;text-align:center'>" +
                "Please noted that the discount code is only available for original price products!</p>" +
                "<p style='color:#999999;font-size:12px;text-align:center'>" +
                "From Shenzhen, China</p>" +
                "<p style='color:#999999;font-size:12px;text-align:center'>" +
                "leelbox-tech.com.</p>" +
                "<p>2206room 17b Keyuan Village Busha Road," +
                "Buji Street Longgang District<br/>" +
                "Shenzhen, Guangdong.CN</p></td>" +
                "</tr></tbody>" +
                "</table></div>" +
                "</div></td><td style='font-family:sans-serif;font-size:14px;vertical-align:top'>&nbsp;</td></tr></tbody></table></div>" +
                "</div>";

        try {
            // 发送优惠券
            emailSendUtilService.send(user.getEmail(), title, text);
            logger.warn("Mail Send : User Regist Success : " + user.getEmail());
        } catch (Exception e) {
            logger.warn("Mail Send Exception : " + e.getMessage());
        }
    }

    @Resource
    SubscribleService subscribleService;

    @EventListener
    public void subscribe(UserSubscribleEvent event) {
        String email = event.getEmail();
        // 邮箱存到订阅库中，方便以后推送
        Subscrible subscrible = new Subscrible();
        subscrible.setEmail(email);
        subscribleService.save(subscrible);
    }

    @Resource
    OrderReceiverService orderReceiverService;

    @EventListener
    public void orderPaid(OrderPaidEvent event) {
        String oid = event.getOid();
        OrderReceiver receiver = orderReceiverService.findBy("oid", oid);
        if (receiver == null) {
            return;
        }
        String email = receiver.getEmail();
        // 发邮件
        String title = "[Leelbox-tech] Order Confirmation";
        String text = "<div>Dear " + receiver.getName() + ", Thanks for your shopping on Leelbox!</div><div><br></div><div>&nbsp;&nbsp;&nbsp;&nbsp;Your order no." + oid + " has been accepted, it will be processed and sent out within 3 days.</div><div><br></div><div>&nbsp;&nbsp;&nbsp;&nbsp;Should you have any question about your order, please feel free to contact us via aftersale@leelbox-tech.com and put your order number on title.&nbsp;</div><div><br></div><div>Thanks again for your shopping!</div>";
        try {
            emailSendUtilService.send(email, title, text);
        } catch (Exception e) {
            // 邮件发送失败
            logger.warn("Mail Send Exception : " + e.getMessage());
        }
    }

    @EventListener
    public void orderSend(OrderSendEvent event) {
        String oid = event.getOid();
        String shipCode = event.getShipCode();
        OrderReceiver receiver = orderReceiverService.findBy("oid", oid);
        if (receiver == null) {
            return;
        }
        String email = receiver.getEmail();
        // 发邮件
        String title = "[Leelbox-tech] Order Confirmation";
        String text = "<div>Dear " + receiver.getName() + ",</div><div><br></div><div>&nbsp;&nbsp;&nbsp;&nbsp;Your order " + oid + " was sent out, this is the tracking number " + shipCode + ", you can check the location of your order via <a href=\"http://www.17track.net/en\" target=\"_blank\">http://www.17track.net/en</a></div><div><br></div><div>&nbsp;&nbsp;&nbsp;&nbsp;Should you have any question about your order, please feel free to contact us via aftersale@leelbox-tech.com and put your order number on title.&nbsp;</div><div><br></div><div>Thanks again for your shopping!</div>";
        try {
            emailSendUtilService.send(email, title, text);
        } catch (Exception e) {
            // 邮件发送失败
            logger.warn("Mail Send Exception : " + e.getMessage());
        }
    }
}
