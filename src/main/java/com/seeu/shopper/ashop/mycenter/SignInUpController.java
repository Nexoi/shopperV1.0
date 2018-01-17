//package com.seeu.shopper.ashop.mycenter;
//
//import com.seeu.oauth.JwtConstant;
//import com.seeu.oauth.JwtUtil;
//import com.seeu.oauth.OAuthType;
//import com.seeu.oauth.SignTokenUser;
//import com.seeu.shopper.eventcenter.UserLoginEvent;
//import com.seeu.shopper.mail.service.EmailSendUtilService;
//import com.seeu.shopper.user.iservice.UserSignService;
//import com.seeu.shopper.user.model.UserLogin;
//import com.seeu.shopper.user.service.UserLoginService;
//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.validator.constraints.Email;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.annotation.Resource;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by neo on 04/09/2017.
// * <p>
// * 登录／注册页面
// */
//@Controller
//public class SignInUpController {
//    @Resource
//    UserSignService signService;
//    @Resource
//    UserLoginService userLoginService;
//    @Autowired
//    JwtUtil jwtUtil;
//    @Autowired
//    JwtConstant jwtConstant;
//    @Autowired
//    ApplicationContext applicationContext;
//
//    @RequestMapping("/signin")
//    public String signInUp() {
//        // 登录／注册页面
//        return "shop/signin";
//    }
//
//    @RequestMapping("/signout")
//    public String signout(HttpServletResponse response) {
//        response.addCookie(new Cookie("token", ""));
//        return "redirect:/";
//    }
//
//    /**
//     * 用户点击登录按钮后
//     *
//     * @return
//     */
//    @PostMapping("/login")
//    public String login(Model model,
//                        HttpServletRequest request,
//                        HttpServletResponse response,
//                        @RequestParam("email") String email,
//                        @RequestParam("password") String password) {
//        // 验证，并注入 token 到 cookie
//        UserLogin user = userLoginService.findBy("email", email.trim().replace(" ", ""));
//        if (user == null || !user.getPassword().equals(password)) {
//            model.addAttribute("code", "400");
//            model.addAttribute("message", "Email or Password Wrong.");
//            return "shop/info";
//        }
//        // 添加登录信息（木有～）
//        // 生成 token 注入
//        SignTokenUser tokenUser = new SignTokenUser();
//        tokenUser.setUid("" + user.getUid());
//        tokenUser.setEmail(user.getEmail());
//        tokenUser.setType(OAuthType.SEEU);
//        tokenUser.setExtra(getIpAddress(request));
//        try {
//            String subject = jwtUtil.generalSubject(tokenUser);
//            String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
//            Cookie cookie = new Cookie("token", token);
//            response.addCookie(cookie);
//        } catch (Exception ex) {
//            // 算作登录失败
//            model.addAttribute("code", "500");
//            model.addAttribute("message", "Web Server Exception.");
//            return "shop/info";
//        }
//        // 登录成功回到首页
//        applicationContext.publishEvent(new UserLoginEvent(this, user));
//        return "redirect:/";
//    }
//
//    @Resource
//    EmailSendUtilService emailSendUtilService;
//
//    @PostMapping("/regist")
//    public String regist(Model model,
//                         HttpServletRequest request,
//                         @RequestParam("name") String name,
//                         @RequestParam("email") String email,
//                         @RequestParam("password") String password,
//                         @RequestParam(value = "subscribe", required = false) Boolean subscribe) {
//        // 添加用户信息
//        if (password.length() < 6) {
//            model.addAttribute("code", "400");
//            model.addAttribute("message", "Password length was too short.");
//            return "shop/info";
//        }
//        UserSignService.status status = signService.registCheck(name, email.trim().replace(" ", ""), password, getIpAddress(request));
//
//        if (status == UserSignService.status.exception) {
//            model.addAttribute("code", "500");
//            model.addAttribute("message", "Web Server Exception.");
//            return "shop/info";
//        }
//
//        if (status == UserSignService.status.isExist) {
//            model.addAttribute("code", "400");
//            model.addAttribute("message", "This email address has been registered.");
//            return "shop/info";
//        }
//        // 验证成功，发送邮件再予以注册
//        // 生成 token 发送给他的邮箱
//        SignTokenUser tokenUser = new SignTokenUser();
//        tokenUser.setUid(name); // 这里寄存用户名
//        tokenUser.setEmail(email.trim().replace(" ", ""));
//        tokenUser.setType(password); // 这里寄存密码
//        tokenUser.setExtra(subscribe == null ? "no" : subscribe ? "yes" : "no");// 是否订阅
//        try {
//            String subject = jwtUtil.generalSubject(tokenUser);
//            String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
//            if (!signService.sendEmail(request, name, email.trim().replace(" ", ""), token)) {
//                model.addAttribute("code", "400");
//                model.addAttribute("message", "Send Error! Email Address is not available.");
//                return "shop/info";
//            }
//        } catch (Exception ex) {
//            // 算作注册失败
//            model.addAttribute("code", "500");
//            model.addAttribute("message", "Web Server Exception.");
//            return "shop/info";
//        }
//        return "shop/signup_success";
//    }
//
//    @RequestMapping("/activemyaccount")
//    public String registCallBack(Model model,
//                                 HttpServletRequest request,
//                                 HttpServletResponse response,
//                                 @RequestParam("code") String code) {
//        SignTokenUser user = null;
//        if (StringUtils.isNotEmpty(code) && (user = jwtUtil.parseToken(code)) != null) {
//            UserLogin userLogin = new UserLogin();
//            userLogin.setLastLoginIp(getIpAddress(request));
//            userLogin.setEmail(user.getEmail());
//            userLogin.setPassword(user.getType());
//            String name = user.getUid();
//            boolean subscribe = "yes".equals(user.getExtra());
//            UserSignService.status status = signService.registCallBack(userLogin, name, subscribe);
//            if (status == UserSignService.status.isExist) {
//                model.addAttribute("code", "400");
//                model.addAttribute("message", "This email address has been registered.");
//                return "shop/info";
//            }
//            if (status == UserSignService.status.exception) {
//                model.addAttribute("code", "500");
//                model.addAttribute("message", "Web Server Exception.");
//                return "shop/info";
//            }
//            if (status == UserSignService.status.success) {
//                // 注入 token
//                UserLogin user_ = userLoginService.findBy("email", userLogin.getEmail());
//                if (user_ != null) {
//                    SignTokenUser tokenUser = new SignTokenUser();
//                    tokenUser.setUid("" + user_.getUid());
//                    tokenUser.setEmail(user_.getEmail());
//                    tokenUser.setType(OAuthType.SEEU);
//                    tokenUser.setExtra(getIpAddress(request));
//                    try {
//                        String subject = jwtUtil.generalSubject(tokenUser);
//                        String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
//                        Cookie cookie = new Cookie("token", token);
//                        response.addCookie(cookie);
//                        return "redirect:/activesuccess";
//                    } catch (Exception ex) {
//                        // 算作注册失败
//                        model.addAttribute("code", "500");
//                        model.addAttribute("message", "Web Server Exception.");
//                        return "shop/info";
//                    }
//                }
//            }
//        }
//        model.addAttribute("code", "400");
//        model.addAttribute("message", "Active Failure. Please try to sign up again");
//        return "shop/info";
//    }
//
//    /**
//     * 有的用户手贱喜欢不断刷新。所以重新写个静态的慢慢刷
//     *
//     * @return
//     */
//    @RequestMapping("/activesuccess")
//    public String registSuccessPage() {
//        return "shop/signup_callback_success";
//    }
//
//    private String getIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        // 如果是多级代理，那么取第一个ip为客户ip
//        if (ip != null && ip.indexOf(",") != -1) {
//            ip = ip.substring(0, ip.indexOf(",")).trim();
//        }
//        return ip;
//    }
//}
