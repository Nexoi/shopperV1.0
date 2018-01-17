package com.seeu.shopper.store.service.impl;

import com.seeu.shopper.store.dao.PageStoreMapper;
import com.seeu.shopper.store.model.PageStore;
import com.seeu.shopper.store.service.PageStoreService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/23.
 */
@Service
@Transactional
public class PageStoreServiceImpl extends AbstractService<PageStore> implements PageStoreService {
    @Resource
    private PageStoreMapper pageStoreMapper;

}
