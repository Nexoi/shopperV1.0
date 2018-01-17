package com.seeu.shopper.support.service.impl;

import com.seeu.shopper.support.dao.SupportMapper;
import com.seeu.shopper.support.model.Support;
import com.seeu.shopper.support.service.SupportService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/14.
 */
@Service
@Transactional
public class SupportServiceImpl extends AbstractService<Support> implements SupportService {
    @Resource
    private SupportMapper supportMapper;

}
