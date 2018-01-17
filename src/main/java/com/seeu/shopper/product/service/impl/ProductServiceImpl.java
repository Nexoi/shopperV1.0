package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductMapper;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.service.ProductService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/11.
 */
@Service
@Transactional
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {
    @Resource
    private ProductMapper productMapper;

}
