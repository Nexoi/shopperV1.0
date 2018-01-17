package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductActivityMapper;
import com.seeu.shopper.product.model.ProductActivity;
import com.seeu.shopper.product.service.ProductActivityService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductActivityServiceImpl extends AbstractService<ProductActivity> implements ProductActivityService {
    @Resource
    private ProductActivityMapper productActivityMapper;

}
