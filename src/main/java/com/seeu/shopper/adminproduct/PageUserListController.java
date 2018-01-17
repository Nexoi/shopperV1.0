package com.seeu.shopper.adminproduct;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.model.UserCoupon;
import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.service.UserAddressService;
import com.seeu.shopper.user.service.UserCouponService;
import com.seeu.shopper.user.service.UserInfoService;
import com.seeu.shopper.user.service.UserLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 25/08/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageUserListController {
    @Resource
    UserInfoService userInfoService;
    @Resource
    UserAddressService userAddressService;
    @Resource
    UserCouponService userCouponService;

    @Resource
    UserLoginService userLoginService;

    @RequestMapping("/userlist.html")
    public String userlist(
            Model model,
            @RequestParam(value = "wd", required = false) String wd,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "by", required = false) String bywhat) {

        // 处理筛选条件
        String property = null;
        if (bywhat == null) {
            bywhat = "email";
        }
        switch (bywhat) {
            case "email":
                property = "email";
                break;
            case "phone":
                property = "phone";
                break;
            case "name":
                property = "userName";
                break;
//            case "status":
//                property = "member_status";
//                break;
            default:
                property = "email";
        }
        // 查询
        PageInfo pageInfo = null;
        if (wd != null && wd.trim().length() != 0) {
            PageHelper.startPage(page, size, "create_date desc");
            Condition condition = new Condition(UserInfo.class);
            condition.createCriteria().andLike(property, "%" + wd + "%");
            List<UserInfo> infoList = userInfoService.findByCondition(condition);
            pageInfo = new PageInfo(infoList);
        } else {
            PageHelper.startPage(page, size, "create_date desc");
            List<UserInfo> infoList = userInfoService.findAll();
            pageInfo = new PageInfo(infoList);
        }

        model.addAttribute("word", wd == null ? "" : wd);
        model.addAttribute("pagelist", pageInfo.getList());
        model.addAttribute("page", pageInfo);
        model.addAttribute("index", pageInfo.getNavigatepageNums());// 页码
        return "adminx/userlist";
    }

    @RequestMapping("/userlist-showDetail.html")
    public String showDetail(
            Model model,
            @RequestParam("uid") Integer uid) {
        if (uid == null) {
            return "404";// 无此用户
        }
        // 查询
        UserInfo userInfo = userInfoService.findById(uid);
        if (userInfo == null) {
            return "404";// 无此用户
        }
        // 地址信息
        Condition condition = new Condition(UserAddress.class);
        condition.createCriteria().andCondition("uid = " + uid);
        List<UserAddress> addressList = userAddressService.findByCondition(condition);
        // 优惠券信息
        Condition condition1 = new Condition(UserCoupon.class);
        condition1.createCriteria().andCondition("uid = " + uid);
        List<UserCoupon> couponList = userCouponService.findByCondition(condition1);

        model.addAttribute("userinfo", userInfo);
        model.addAttribute("address", addressList);
        model.addAttribute("coupons", couponList);
        return "adminx/userlist-modal-showdetail";
    }

    @RequestMapping("/userlist-showAccount.html")
    public String showAccount(
            Model model,
            @RequestParam("uid") Integer uid) {
        UserLogin userLogin = userLoginService.findById(uid);
        // 无论有没有此账户，都注入数据（空就好）
        if (userLogin == null) {
            userLogin = new UserLogin();
            userLogin.setUid(uid);
            userLogin.setEmail("");
            userLogin.setPassword("");
        }
        model.addAttribute("user", userLogin);
        return "adminx/userlist-modal-showaccount";
    }
}
