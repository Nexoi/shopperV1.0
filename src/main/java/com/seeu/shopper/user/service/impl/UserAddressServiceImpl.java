package com.seeu.shopper.user.service.impl;

import com.seeu.shopper.user.dao.UserAddressMapper;
import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.service.UserAddressService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/25.
 */
@Service
@Transactional
public class UserAddressServiceImpl extends AbstractService<UserAddress> implements UserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;

}
