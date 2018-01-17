package com.seeu.shopper.order.service.impl;

import com.seeu.shopper.order.dao.OrderProductMapper;
import com.seeu.shopper.order.model.OrderProduct;
import com.seeu.shopper.order.service.OrderProductService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/10.
 */
@Service
@Transactional
public class OrderProductServiceImpl extends AbstractService<OrderProduct> implements OrderProductService {
    @Resource
    private OrderProductMapper orderProductMapper;

}
