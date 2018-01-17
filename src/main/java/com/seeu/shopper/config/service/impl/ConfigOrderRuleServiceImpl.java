package com.seeu.shopper.config.service.impl;

import com.seeu.shopper.config.dao.ConfigOrderRuleMapper;
import com.seeu.shopper.config.model.ConfigOrderRule;
import com.seeu.shopper.config.service.ConfigOrderRuleService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/29.
 */
@Service
@Transactional
public class ConfigOrderRuleServiceImpl extends AbstractService<ConfigOrderRule> implements ConfigOrderRuleService {
    @Resource
    private ConfigOrderRuleMapper configOrderRuleMapper;

}
