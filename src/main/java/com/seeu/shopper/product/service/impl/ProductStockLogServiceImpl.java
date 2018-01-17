package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductStockLogMapper;
import com.seeu.shopper.product.model.ProductStockLog;
import com.seeu.shopper.product.service.ProductStockLogService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductStockLogServiceImpl extends AbstractService<ProductStockLog> implements ProductStockLogService {
    @Resource
    private ProductStockLogMapper productStockLogMapper;

}
