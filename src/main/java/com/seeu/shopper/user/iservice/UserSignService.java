package com.seeu.shopper.user.iservice;

import com.seeu.shopper.eventcenter.UserRegistEvent;
import com.seeu.shopper.eventcenter.UserSubscribleEvent;
import com.seeu.shopper.mail.service.EmailSendUtilService;
import com.seeu.shopper.paypal.util.URLUtils;
import com.seeu.shopper.user.dao.UserLoginMapper;
import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.service.UserInfoService;
import com.seeu.shopper.user.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by neo on 05/09/2017.
 */
@Service
public class UserSignService {
    @Resource
    UserLoginMapper mapper;
    @Resource
    UserLoginService userLoginService;
    @Resource
    UserInfoService userInfoService;
    @Autowired
    ApplicationContext applicationContext;


    public enum status {
        isExist, exception, success
    }

    public status registCheck(String name, String email, String password, String ip) {
        // 添加用户信息，返回注册成功与否，以及对应状态

        // 查看邮箱是否已经被注册
        UserLogin existUser = userLoginService.findBy("email", email);
//        if (existUser != null) {
//            return status.isExist;// 已经被注册
//        } else {
//            return status.success;
//        }
        return existUser != null ? status.isExist : status.success;
    }


    @Resource
    EmailSendUtilService emailSendUtilService;

    /**
     * 发送验证信息到邮箱，确认后方可登录
     *
     * @param email
     * @param token
     * @return
     */
    public boolean sendEmail(HttpServletRequest request, String name, String email, String token) {

//        String url = URLUtils.getBaseURl(request) + "/activemyaccount?code=" + token;
        String url = "https://leelbox-tech.com/activemyaccount?code=" + token;
        String text = "<div>" +
                "<div style='background-color:#ffffff;font-family:sans-serif;font-size:14px;line-height:1.4;margin:0;padding:0'>" +
                "<div style='color: #fff !important;background-color: rgb(42, 141, 197); padding: 20px !important;'>" +
                "<h2 style='margin-top: 40px;'>" +
                "Leelbox-tech" +
                "</h2>" +
                "<h4>" +
                "Click the link below to activate your account" +
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
                "Welcome, " + name + "!</p>" +
                "<p style='font-family:sans-serif;font-size:14px;font-weight:normal;margin:0;Margin-bottom:15px'>" +
                "This is a message to activate the account." +
                "</p>" +
                "<p style='font-family:sans-serif;font-size:14px;font-weight:normal;margin:0;Margin-bottom:15px'>" +
                "After activating the account," +
                "You will be able to enjoy the fun of shopping on our website.</p>" +
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
                "ACTIVE" +
                "</a></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table><div style='clear:both;padding-top:10px;text-align:center;width:100%'>" +
                "<table border='0' cellpadding='0' cellspacing='0'" +
                "style='border-collapse:separate;width:100%'>" +
                "<tbody>" +
                "<tr>" +
                "<td style='font-family:sans-serif;font-size:14px;vertical-align:top;color:#999999;font-size:12px;text-align:center'>" +
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
            return emailSendUtilService.send(email, "Welcome to Leelbox-tech", text);
        } catch (Exception e) {
            return false;
        }
    }


    public status registCallBack(UserLogin user, String name, boolean subscribe) {
        UserLogin existUser = userLoginService.findBy("email", user.getEmail());
        if (existUser != null) {
            return status.isExist;
        }
        mapper.insertNewUser(user);
        if (user != null) {
            // 添加信息
            UserInfo info = new UserInfo();
            info.setEmail(user.getEmail());
            info.setUid(user.getUid());
            info.setUserName(name);
            info.setMemberStatus(1);// 1 表示正常用户
            userInfoService.save(info);
            // 通知
            applicationContext.publishEvent(new UserRegistEvent(this, user, name));
            if (subscribe) applicationContext.publishEvent(new UserSubscribleEvent(this, user.getEmail()));
            return status.success;
        }
        return status.exception;
    }
}
