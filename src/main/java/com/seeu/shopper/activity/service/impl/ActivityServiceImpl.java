package com.seeu.shopper.activity.service.impl;

import com.seeu.shopper.activity.dao.ActivityMapper;
import com.seeu.shopper.activity.model.Activity;
import com.seeu.shopper.activity.service.ActivityService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/12.
 */
@Service
@Transactional
public class ActivityServiceImpl extends AbstractService<Activity> implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;

}
