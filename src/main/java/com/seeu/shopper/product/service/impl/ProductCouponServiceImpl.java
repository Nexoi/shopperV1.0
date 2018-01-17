package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductCouponMapper;
import com.seeu.shopper.product.model.ProductCoupon;
import com.seeu.shopper.product.service.ProductCouponService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/30.
 */
@Service
@Transactional
public class ProductCouponServiceImpl extends AbstractService<ProductCoupon> implements ProductCouponService {
    @Resource
    private ProductCouponMapper productCouponMapper;

}
