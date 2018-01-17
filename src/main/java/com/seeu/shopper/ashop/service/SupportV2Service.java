package com.seeu.shopper.ashop.service;

import com.seeu.shopper.ashop.dao.SupportV2Mapper;
import com.seeu.shopper.store.model.PageStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 27/09/2017.
 */
@Service
public class SupportV2Service {
    @Resource
    SupportV2Mapper supportV2Mapper;

    public List<PageStore> selectSupportCateOnlyName() {
        return supportV2Mapper.selectSupportCateOnlyName();
    }

}
