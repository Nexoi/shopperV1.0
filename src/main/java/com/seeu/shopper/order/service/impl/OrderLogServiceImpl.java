package com.seeu.shopper.order.service.impl;

import com.seeu.shopper.order.dao.OrderLogMapper;
import com.seeu.shopper.order.model.OrderLog;
import com.seeu.shopper.order.service.OrderLogService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/19.
 */
@Service
@Transactional
public class OrderLogServiceImpl extends AbstractService<OrderLog> implements OrderLogService {
    @Resource
    private OrderLogMapper orderLogMapper;

}
