package com.seeu.shopper.user.service.impl;

import com.seeu.shopper.user.dao.UserInfoMapper;
import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.service.UserInfoService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/17.
 */
@Service
@Transactional
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

}
