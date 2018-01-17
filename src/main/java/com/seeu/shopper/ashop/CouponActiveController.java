package com.seeu.shopper.ashop;

import com.seeu.shopper.coupon.iservice.ICouponDecodeService;
import com.seeu.shopper.user.model.UserCoupon;
import com.seeu.shopper.user.service.UserCouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by neo on 30/09/2017.
 */
@Controller
@RequestMapping("/activecoupon")
public class CouponActiveController {
    @Resource
    ICouponDecodeService iCouponDecodeService;
    @Resource
    UserCouponService userCouponService;

    @RequestMapping("/{coupon}")
    public String activeCoupon(Model model,
                               @RequestAttribute("uid") Integer uid,
                               @PathVariable("coupon") String coupon) {
        String cid = iCouponDecodeService.decode(coupon);
        if (cid == null) {
            model.addAttribute("code", 400);
            model.addAttribute("message", "coupon is not available.");
            return "shop/info";
        }

        // 先查询是否已经被添加
        UserCoupon existCoupon = userCouponService.findBy("code", coupon);
        if (existCoupon != null) {
            model.addAttribute("code", 400);
            model.addAttribute("message", "coupon has been activated.");
            return "shop/info";
        }

        // 绑定到用户目录下
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUid(uid);
        userCoupon.setStatus(0);// 未启用
        userCoupon.setCode(coupon);
        userCoupon.setCid(cid);
        userCouponService.save(userCoupon);
        model.addAttribute("code", "Active Successfully!");
        model.addAttribute("message", "Coupon have been added to the personal center");
        return "shop/info";
    }
}
