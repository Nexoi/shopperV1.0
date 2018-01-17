package com.seeu.shopper.store.service.impl;

import com.seeu.shopper.store.dao.DbStoreMapper;
import com.seeu.shopper.store.model.DbStore;
import com.seeu.shopper.store.service.DbStoreService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/23.
 */
@Service
@Transactional
public class DbStoreServiceImpl extends AbstractService<DbStore> implements DbStoreService {
    @Resource
    private DbStoreMapper dbStoreMapper;

}
