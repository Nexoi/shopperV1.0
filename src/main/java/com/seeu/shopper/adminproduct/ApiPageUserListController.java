package com.seeu.shopper.adminproduct;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.service.UserInfoService;
import com.seeu.shopper.user.service.UserLoginService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by neo on 25/08/2017.
 *
 * 提供给管理员修改用户信息
 */
@RestController
@RequestMapping("/api/admin/v1/userlist")
public class ApiPageUserListController {
    @Resource
    UserInfoService userInfoService;
    @Resource
    UserLoginService userLoginService;


    @PutMapping("userinfo")
    public Result updateUserInfo(UserInfo info) {
        if (info.getUid() != null) {
            userInfoService.update(info);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("请确认传入参数是否完整");
    }

    @PutMapping("userlogin")
    public Result updateUserLogin(UserLogin userLogin) {
        if (userLogin.getUid() != null) {
            int lines = userLoginService.update(userLogin);// 返回更新的行数
            if (lines == 0) {
                // 无更新，则添加数据
                userLoginService.save(userLogin);
            }
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("请确认传入参数是否完整");
    }
}
