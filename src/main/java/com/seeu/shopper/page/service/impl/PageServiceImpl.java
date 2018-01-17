package com.seeu.shopper.page.service.impl;

import com.seeu.shopper.page.dao.PageMapper;
import com.seeu.shopper.page.model.Page;
import com.seeu.shopper.page.service.PageService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/02.
 */
@Service
@Transactional
public class PageServiceImpl extends AbstractService<Page> implements PageService {
    @Resource
    private PageMapper pageMapper;

}
