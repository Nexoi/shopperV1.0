package com.seeu.shopper.config.service.impl;

import com.seeu.shopper.config.dao.ConfigMapper;
import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.config.service.ConfigService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/28.
 */
@Service
@Transactional
public class ConfigServiceImpl extends AbstractService<Config> implements ConfigService {
    @Resource
    private ConfigMapper configMapper;

}
