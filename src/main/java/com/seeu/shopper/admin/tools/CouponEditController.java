package com.seeu.shopper.admin.tools;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.coupon.model.Coupon;
import com.seeu.shopper.coupon.service.CouponService;
import com.seeu.shopper.product.model.ProductCoupon;
import com.seeu.shopper.product.service.ProductCouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 优惠券操作
 * <p>
 * Created by neo on 30/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class CouponEditController {

    @Resource
    CouponService couponService;


    @RequestMapping("/coupons.html")
    public String showAll(Model model,
                          @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        PageHelper.startPage(page, size, "create_time desc");
        List<Coupon> couponList = couponService.findAll();
        PageInfo pageInfo = new PageInfo(couponList);

        model.addAttribute("pagelist", pageInfo.getList());
        model.addAttribute("page", pageInfo);
        model.addAttribute("index", pageInfo.getNavigatepageNums());// 页码
        return "adminx/coupons";
    }

    @RequestMapping("/coupons-add.html")
    public String addCoupon() {
        return "adminx/coupons-add";
    }
}
