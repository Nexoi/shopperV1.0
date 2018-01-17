package com.seeu.shopper.support.service.impl;

import com.seeu.shopper.support.dao.SupportCateMapper;
import com.seeu.shopper.support.model.SupportCate;
import com.seeu.shopper.support.service.SupportCateService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/14.
 */
@Service
@Transactional
public class SupportCateServiceImpl extends AbstractService<SupportCate> implements SupportCateService {
    @Resource
    private SupportCateMapper supportCateMapper;

}
