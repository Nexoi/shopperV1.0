package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductBannerMapper;
import com.seeu.shopper.product.model.ProductBanner;
import com.seeu.shopper.product.service.ProductBannerService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/11.
 */
@Service
@Transactional
public class ProductBannerServiceImpl extends AbstractService<ProductBanner> implements ProductBannerService {
    @Resource
    private ProductBannerMapper productBannerMapper;

}
