package com.seeu.shopper.admin.signin;

import com.seeu.oauth.JwtConstant;
import com.seeu.oauth.JwtUtil;
import com.seeu.oauth.OAuthType;
import com.seeu.oauth.SignTokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by neo on 26/08/2017.
 */
@Controller
@RequestMapping("/adminx")
public class AdminSigninController {

    @RequestMapping("/signin.html")
    public String page() {
        return "adminx/signin";
    }

    @Autowired
    JwtConstant jwtConstant;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login.html")
    public String loginIt(Model model,
                          HttpServletResponse response,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password) {
        if (username.equals("leelbox-admin") && password.equals("leelbox-ps123")) {

            SignTokenUser tokenUser = new SignTokenUser();
            tokenUser.setUid("1");
            tokenUser.setEmail("admin");
            tokenUser.setType(OAuthType.ADMIN);
            try {
                String subject = jwtUtil.generalSubject(tokenUser);
                String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
                Cookie cookie = new Cookie("stoken", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/adminx/main";
            } catch (Exception ex) {
                // 算作登录失败
                model.addAttribute("code", "500");
                model.addAttribute("message", "Web Server Exception.");
                return "shop/info";
            }
        } else {
            model.addAttribute("code", "登录失败");
            model.addAttribute("message", "请确定账户密码是否正确");
            return "adminx/info";
        }
    }

}
