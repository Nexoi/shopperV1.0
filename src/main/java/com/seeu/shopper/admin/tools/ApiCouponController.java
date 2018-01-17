package com.seeu.shopper.admin.tools;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.adminproduct.service.PageProductService;
import com.seeu.shopper.coupon.iservice.ICouponService;
import com.seeu.shopper.coupon.model.Coupon;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.model.ProductCoupon;
import com.seeu.shopper.product.service.ProductCouponService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 29/09/2017.
 */
@RequestMapping("/api/admin/v1/coupon")
@RestController
public class ApiCouponController {

    @Resource
    ICouponService iCouponService;
    @Resource
    ProductCouponService productCouponService;

    /**
     * 创建新优惠券
     *
     * @param coupon
     * @return
     */
    @PostMapping("/private")
    public Result addCouponPrivate20(Coupon coupon) {
        String cid = iCouponService.addCoupon(coupon, ICouponService.COUPON_TYPE.PRIVATE);
        return ResultGenerator.genSuccessResult(cid);
    }

    @PostMapping("/public")
    public Result addCouponPublic5(Coupon coupon) {
        String cid = iCouponService.addCoupon(coupon, ICouponService.COUPON_TYPE.PUBLIC);
        return ResultGenerator.genSuccessResult(cid);
    }

    @DeleteMapping("/{cid}")
    public Result del(@PathVariable("cid") String cid) {
        // 删除优惠券信息
        iCouponService.deleteCoupon(cid);
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 生成优惠券码
     *
     * @param cid
     * @param count
     * @return 一系列优惠码
     */
    @PostMapping("create")
    public Result createCoupons(@RequestParam("cid") String cid,
                                @RequestParam("count") Integer count) {
        List<String> list = iCouponService.createCoupons(cid, count);
        if (list == null) return ResultGenerator.genFailResult("生成失败");
        return ResultGenerator.genSuccessResult(list);
    }


    /////////////////////////////// 优惠券和商品信息绑定 ////////////////////////////////////////////

    @GetMapping("/products/{cid}")
    public Result getAll(@PathVariable("cid") String cid) {
        Condition condition = new Condition(ProductCoupon.class);
        condition.createCriteria().andCondition("cid = '" + cid + "'");
        List<ProductCoupon> coupons = productCouponService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(coupons);
    }

    /**
     * 绑定优惠券
     *
     * @param cid
     * @param products strings, like : 43,56,23,88,67
     * @return
     */
    @PostMapping("/available")
    public Result bindProducts(@RequestParam("cid") String cid, @RequestParam("products") String products) {
        String[] pids = products.split(",");
        List<ProductCoupon> couponList = new ArrayList();
        for (String pid : pids) {
            ProductCoupon coupon = new ProductCoupon();
            coupon.setCid(cid);
            coupon.setPid(Integer.parseInt(pid));
            couponList.add(coupon);
        }
        // 规整化正确之后：
        productCouponService.save(couponList);
        return ResultGenerator.genSuccessResult();
    }

    @Resource
    PageProductService pageProductService;

    /**
     * 绑定优惠券到所有的商品
     *
     * @param cid
     * @return
     */
    @PostMapping("/available-all")
    public Result bindAllProducts(@RequestParam("cid") String cid) {
        List<Product> pids = pageProductService.selectAllNames();
        List<ProductCoupon> couponList = new ArrayList();
        for (Product product : pids) {
            ProductCoupon coupon = new ProductCoupon();
            coupon.setCid(cid);
            coupon.setPid(product.getPid());
            couponList.add(coupon);
        }
        productCouponService.save(couponList);
        return ResultGenerator.genSuccessResult();
    }
}
