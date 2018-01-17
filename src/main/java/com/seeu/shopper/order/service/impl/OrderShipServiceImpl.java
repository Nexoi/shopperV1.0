package com.seeu.shopper.order.service.impl;

import com.seeu.shopper.order.dao.OrderShipMapper;
import com.seeu.shopper.order.model.OrderShip;
import com.seeu.shopper.order.service.OrderShipService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/18.
 */
@Service
@Transactional
public class OrderShipServiceImpl extends AbstractService<OrderShip> implements OrderShipService {
    @Resource
    private OrderShipMapper orderShipMapper;

}
