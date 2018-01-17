package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductStockMapper;
import com.seeu.shopper.product.model.ProductStock;
import com.seeu.shopper.product.service.ProductStockService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductStockServiceImpl extends AbstractService<ProductStock> implements ProductStockService {
    @Resource
    private ProductStockMapper productStockMapper;

}
