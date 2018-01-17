package com.seeu.shopper.user.service.impl;

import com.seeu.shopper.user.dao.UserLoginMapper;
import com.seeu.shopper.user.model.UserLogin;
import com.seeu.shopper.user.service.UserLoginService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/25.
 */
@Service
@Transactional
public class UserLoginServiceImpl extends AbstractService<UserLogin> implements UserLoginService {
    @Resource
    private UserLoginMapper userLoginMapper;

}
