package com.seeu.shopper.adminproduct.service;

import com.seeu.shopper.adminproduct.dao.PageProductNormMapper;
import com.seeu.shopper.product.model.ProductNorm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by neo on 26/08/2017.
 */
@Service
public class PageProductNormService {
    @Resource
    PageProductNormMapper pageProductNormMapper;

    public Integer insertProductNorm(ProductNorm norm) {
        return pageProductNormMapper.insertSelective2(norm);
    }
}
