package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductNormMapper;
import com.seeu.shopper.product.model.ProductNorm;
import com.seeu.shopper.product.service.ProductNormService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductNormServiceImpl extends AbstractService<ProductNorm> implements ProductNormService {
    @Resource
    private ProductNormMapper productNormMapper;

}
