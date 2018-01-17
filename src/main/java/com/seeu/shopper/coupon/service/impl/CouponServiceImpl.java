package com.seeu.shopper.coupon.service.impl;

import com.seeu.shopper.coupon.dao.CouponMapper;
import com.seeu.shopper.coupon.model.Coupon;
import com.seeu.shopper.coupon.service.CouponService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/30.
 */
@Service
@Transactional
public class CouponServiceImpl extends AbstractService<Coupon> implements CouponService {
    @Resource
    private CouponMapper couponMapper;

}
