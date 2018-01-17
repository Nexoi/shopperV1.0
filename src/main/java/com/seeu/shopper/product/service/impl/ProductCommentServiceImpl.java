package com.seeu.shopper.product.service.impl;

import com.seeu.shopper.product.dao.ProductCommentMapper;
import com.seeu.shopper.product.model.ProductComment;
import com.seeu.shopper.product.service.ProductCommentService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/28.
 */
@Service
@Transactional
public class ProductCommentServiceImpl extends AbstractService<ProductComment> implements ProductCommentService {
    @Resource
    private ProductCommentMapper productCommentMapper;

}
