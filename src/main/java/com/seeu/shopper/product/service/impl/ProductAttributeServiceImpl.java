package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductAttributeMapper;
import com.seeu.shopper.product.model.ProductAttribute;
import com.seeu.shopper.product.service.ProductAttributeService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductAttributeServiceImpl extends AbstractService<ProductAttribute> implements ProductAttributeService {
    @Resource
    private ProductAttributeMapper productAttributeMapper;

}
