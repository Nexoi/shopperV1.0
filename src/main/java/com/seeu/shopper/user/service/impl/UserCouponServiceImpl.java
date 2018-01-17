package com.seeu.shopper.user.service.impl;

import com.seeu.shopper.user.dao.UserCouponMapper;
import com.seeu.shopper.user.model.UserCoupon;
import com.seeu.shopper.user.service.UserCouponService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/25.
 */
@Service
@Transactional
public class UserCouponServiceImpl extends AbstractService<UserCoupon> implements UserCouponService {
    @Resource
    private UserCouponMapper userCouponMapper;

}
