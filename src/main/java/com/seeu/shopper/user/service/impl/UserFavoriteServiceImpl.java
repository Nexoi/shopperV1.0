package com.seeu.shopper.user.service.impl;

import com.seeu.shopper.user.dao.UserFavoriteMapper;
import com.seeu.shopper.user.model.UserFavorite;
import com.seeu.shopper.user.service.UserFavoriteService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/08/25.
 */
@Service
@Transactional
public class UserFavoriteServiceImpl extends AbstractService<UserFavorite> implements UserFavoriteService {
    @Resource
    private UserFavoriteMapper userFavoriteMapper;

}
