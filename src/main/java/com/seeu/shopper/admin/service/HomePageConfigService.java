package com.seeu.shopper.admin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.ashop.domain.FullPageModel;
import com.seeu.shopper.ashop.domain.footer.FooterModel;
import com.seeu.shopper.ashop.domain.header.HeaderModel;
import com.seeu.shopper.ashop.domain.index.IndexModel;
import com.seeu.shopper.ashop.domain.index.MainCate;
import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.page.model.Page;
import com.seeu.shopper.page.service.PageService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 20/09/2017.
 */
@Service
public class HomePageConfigService {
    @Resource
    PageService pageService;

    public Result updateHomeConfig(IndexModel indexModel){
        String json = JSON.toJSON(indexModel).toString();
        Page page = new Page();
        page.setPageName("home");
        page.setConfig(json);

        Condition condition = new Condition(Page.class);
        condition.createCriteria().andCondition("page_name = 'home'");
        int i = pageService.updateCondition(page, condition);
        if (0 == i) {
            return ResultGenerator.genNoContentResult("无此配置信息");
        }
        // 刷新配置信息
        FullPageModel.flushHeaderModel();
        FullPageModel.flushIndexModel();
        return ResultGenerator.genSuccessResult();
    }
    public Result updateHeaderConfig(HeaderModel headerModel){
        String json = JSON.toJSON(headerModel).toString();
        Page page = new Page();
        page.setPageName("header");
        page.setConfig(json);

        Condition condition = new Condition(Page.class);
        condition.createCriteria().andCondition("page_name = 'header'");
        int i = pageService.updateCondition(page, condition);
        if (0 == i) {
            return ResultGenerator.genNoContentResult("无此配置信息");
        }
        // 刷新配置信息
        FullPageModel.flushHeaderModel();
        FullPageModel.flushIndexModel();
        return ResultGenerator.genSuccessResult();
    }

    public Result updateFooterConfig(FooterModel footerModel){
        String json = JSON.toJSON(footerModel).toString();
        Page page = new Page();
        page.setPageName("footer");
        page.setConfig(json);

        Condition condition = new Condition(Page.class);
        condition.createCriteria().andCondition("page_name = 'footer'");
        int i = pageService.updateCondition(page, condition);
        if (0 == i) {
            return ResultGenerator.genNoContentResult("无此配置信息");
        }
        // 刷新配置信息
        FullPageModel.flushFooterModel();
        FullPageModel.flushIndexModel();
        return ResultGenerator.genSuccessResult();
    }
}
