package com.seeu.shopper.admin.service;

import com.seeu.shopper.store.model.PageStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 23/09/2017.
 */
@Service
public class IPageStoreService {

    @Resource
    com.seeu.shopper.store.dao.iPageStoreMapper iPageStoreMapper;

    public List<PageStore> queryListAll(){
        return iPageStoreMapper.queryListAll();
    }
}
