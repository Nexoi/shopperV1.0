package com.seeu.shopper.subscrible.service.impl;

import com.seeu.shopper.subscrible.dao.SubscribleMapper;
import com.seeu.shopper.subscrible.model.Subscrible;
import com.seeu.shopper.subscrible.service.SubscribleService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/28.
 */
@Service
@Transactional
public class SubscribleServiceImpl extends AbstractService<Subscrible> implements SubscribleService {
    @Resource
    private SubscribleMapper subscribleMapper;

}
