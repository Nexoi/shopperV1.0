package com.seeu.shopper.order.service.impl;

import com.seeu.shopper.order.dao.OrderReceiverMapper;
import com.seeu.shopper.order.model.OrderReceiver;
import com.seeu.shopper.order.service.OrderReceiverService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/31.
 */
@Service
@Transactional
public class OrderReceiverServiceImpl extends AbstractService<OrderReceiver> implements OrderReceiverService {
    @Resource
    private OrderReceiverMapper orderReceiverMapper;

}
