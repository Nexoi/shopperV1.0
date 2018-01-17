package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductCategoryMapper;
import com.seeu.shopper.product.model.ProductCategory;
import com.seeu.shopper.product.service.ProductCategoryService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductCategoryServiceImpl extends AbstractService<ProductCategory> implements ProductCategoryService {
    @Resource
    private ProductCategoryMapper productCategoryMapper;

}
