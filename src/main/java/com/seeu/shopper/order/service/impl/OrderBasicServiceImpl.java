package com.seeu.shopper.order.service.impl;

import com.seeu.shopper.order.dao.OrderBasicMapper;
import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/09.
 */
@Service
@Transactional
public class OrderBasicServiceImpl extends AbstractService<OrderBasic> implements OrderBasicService {
    @Resource
    private OrderBasicMapper orderBasicMapper;

}
