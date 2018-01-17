package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductImgMapper;
import com.seeu.shopper.product.model.ProductImg;
import com.seeu.shopper.product.service.ProductImgService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductImgServiceImpl extends AbstractService<ProductImg> implements ProductImgService {
    @Resource
    private ProductImgMapper productImgMapper;

}
