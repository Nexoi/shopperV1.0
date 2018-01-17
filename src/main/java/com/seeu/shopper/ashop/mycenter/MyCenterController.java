package com.seeu.shopper.ashop.mycenter;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.ashop.domain.mycenter.ProductNameService;
import com.seeu.shopper.coupon.model.Coupon;
import com.seeu.shopper.coupon.service.CouponService;
import com.seeu.shopper.product.model.ProductComment;
import com.seeu.shopper.product.service.ProductCommentService;
import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.model.UserCoupon;
import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.service.UserAddressService;
import com.seeu.shopper.user.service.UserCouponService;
import com.seeu.shopper.user.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 05/09/2017.
 */
@Controller
public class MyCenterController {
    @Resource
    UserAddressService userAddressService;
    @Resource
    UserInfoService userInfoService;
    @Resource
    ProductCommentService productCommentService;
    @Resource
    ProductNameService productNameService;
    @Resource
    UserCouponService userCouponService;
    @Resource
    CouponService couponService;

    @RequestMapping("/mycenter")
    public String mycenter() {
        return "redirect:/mycenter/order";
    }


    @RequestMapping("/mycenter/address")
    public String myaddress(Model model,
                            @RequestAttribute("uid") Integer uid) {

        Condition condition = new Condition(UserAddress.class);
        condition.createCriteria().andCondition("uid = " + uid);
        List<UserAddress> addressList = userAddressService.findByCondition(condition);
        model.addAttribute("adrsList", addressList);
        return "shop/mycenter/address";
    }


    @RequestMapping("/mycenter/setting")
    public String accountSetting(Model model,
                                 @RequestAttribute("uid") Integer uid) {

        UserInfo userInfo = userInfoService.findById(uid);
        model.addAttribute("info", userInfo);
        return "shop/mycenter/setting";
    }

    /**
     * 个人对商品的所有评论
     *
     * @return
     */
    @RequestMapping("/mycenter/reviews")
    public String myReview(Model model,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "size", required = false) Integer size,
                           @RequestAttribute("uid") Integer uid) {
        if (page == null) page = 0;
        size = 5;
        PageHelper.startPage(page, size, "create_date desc"); // order by create_date
        Condition condition = new Condition(ProductComment.class);
        condition.createCriteria().andCondition("uid = " + uid);
        List<ProductComment> comments = productCommentService.findByCondition(condition);
        // 把 userName 换成 productName
        for (ProductComment comment : comments) {
            comment.setName(productNameService.getName(comment.getPid()));
        }
        PageInfo info = new PageInfo(comments);

        model.addAttribute("pagelist", info.getList());
        model.addAttribute("page", info);
        return "shop/mycenter/review";
    }

    @RequestMapping("/mycenter/coupon")
    public String myCoupons(Model model,
                            @RequestAttribute("uid") Integer uid) {
        Condition condition = new Condition(UserCoupon.class);
        condition.createCriteria().andCondition("uid = " + uid + " AND status = 0");
        List<UserCoupon> coupons = userCouponService.findByCondition(condition);
        // 查询对应的优惠券详情信息
        List<Coupon> couponList = new ArrayList<>();
        for (UserCoupon coupon : coupons) {
            Coupon c = couponService.findBy("cid", coupon.getCid());
            if (c != null) {
                // img 缓存 code 字段
                c.setImg(coupon.getCode());
                couponList.add(c);
            }
        }
        model.addAttribute("size",couponList.size());
        model.addAttribute("list", couponList);
        return "shop/mycenter/coupon";
    }
}
