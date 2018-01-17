package com.seeu.shopper.ashop.mycenter;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.service.UserInfoService;
import com.seeu.shopper.user.service.UserLoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by neo on 11/09/2017.
 */
@RestController
@RequestMapping("/api/shop/v1/ct/setting")
public class ApiAccountSettingController {

    @Resource
    UserInfoService userInfoService;
    @Resource
    UserLoginService userLoginService;

    @PutMapping("/name")
    public Result editName(@RequestAttribute("uid") Integer uid,
                           @RequestParam(value = "userName", required = true) String userName) {
        UserInfo info = new UserInfo();
        info.setUid(uid);
        info.setUserName(userName);
        userInfoService.update(info);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/email")
    public Result editEmail(@RequestAttribute("uid") Integer uid,
                            @RequestParam(value = "email", required = true) String email) {
        UserInfo info = new UserInfo();
        info.setUid(uid);
        info.setEmail(email);
        userInfoService.update(info);
        return ResultGenerator.genSuccessResult();
    }


    @PutMapping("/passwd")
    public Result editPassword(@RequestAttribute("uid") Integer uid,
                               HttpServletResponse response,
                               @RequestParam(value = "passwd", required = true) String password,
                               @RequestParam(value = "newPasswd", required = true) String newPassword) {


        UserLogin user = userLoginService.findById(uid);
        if (user == null) return ResultGenerator.genFailResult("user not exist");
        if (user.getPassword().equals(password)) {
            user.setPassword(newPassword);
            user.setEmail(null);
            user.setLastLoginTime(null);
            user.setLastLoginIp(null);
            userLoginService.update(user);
            // 清除登录 token
            Cookie cookie = new Cookie("token", "");
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("wrong password ! please check your password again.");
    }

}
