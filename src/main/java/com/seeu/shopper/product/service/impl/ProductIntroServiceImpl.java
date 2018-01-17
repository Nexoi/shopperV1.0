package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductIntroMapper;
import com.seeu.shopper.product.model.ProductIntro;
import com.seeu.shopper.product.service.ProductIntroService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/27.
 */
@Service
@Transactional
public class ProductIntroServiceImpl extends AbstractService<ProductIntro> implements ProductIntroService {
    @Resource
    private ProductIntroMapper productIntroMapper;

}
