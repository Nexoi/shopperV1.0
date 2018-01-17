package com.seeu.shopper.third.service.impl;

import com.seeu.shopper.third.dao.Third1Mapper;
import com.seeu.shopper.third.model.Third1;
import com.seeu.shopper.third.service.Third1Service;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/10/09.
 */
@Service
@Transactional
public class Third1ServiceImpl extends AbstractService<Third1> implements Third1Service {
    @Resource
    private Third1Mapper third1Mapper;

}
