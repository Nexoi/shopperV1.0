package com.seeu.shopper.ashop.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seeu.shopper.ashop.domain.footer.FooterModel;
import com.seeu.shopper.ashop.domain.header.HeaderModel;
import com.seeu.shopper.ashop.domain.index.IndexModel;
import com.seeu.shopper.ashop.service.SupportV2Service;
import com.seeu.shopper.page.model.Page;
import com.seeu.shopper.page.service.PageService;
import com.seeu.shopper.store.model.PageStore;
import com.seeu.shopper.store.service.PageStoreService;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 03/09/2017.
 */
public class FullPageModel {

    private static IndexModel indexModel = null;
    private static HeaderModel headerModel = null;
    private static FooterModel footerModel = null;
    private static List<PageStore> pageStores = null; // for support

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static IndexModel getIndexModel(PageService service) {
        if (indexModel == null) {
            Page page = service.findBy("pageName", "home");
            if (page != null) {
                JSONObject jo = JSON.parseObject(page.getConfig());
                IndexModel model = JSON.toJavaObject(jo, IndexModel.class);
                if (model != null) {
                    // 装载 header （因为此页面需要 header 的分类数据，且：此页面不含有通用 header）
                    HeaderModel headerModel = getHeaderModel(service);
                    model.setCategory(headerModel.getCategory());
                    indexModel = model;
                }
            }
        }
        return indexModel;
    }

    public static void flushIndexModel() {
        indexModel = null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static HeaderModel getHeaderModel(PageService service) {
        if (headerModel == null) {
            Page page = service.findBy("pageName", "header");
            if (page != null) {
                JSONObject jo = JSON.parseObject(page.getConfig());
                HeaderModel model = JSON.toJavaObject(jo, HeaderModel.class);
                if (model != null) {
                    headerModel = model;
                }
            } else {
                headerModel = new HeaderModel();
                headerModel.setCategory(new ArrayList<>());
            }
        }
        return headerModel;
    }

    public static void flushHeaderModel() {
        footerModel = null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static FooterModel getFooterModel(PageService service) {
        if (footerModel == null) {
            Page page = service.findBy("pageName", "footer");
            if (page != null) {
                JSONObject jo = JSON.parseObject(page.getConfig());
                FooterModel model = JSON.toJavaObject(jo, FooterModel.class);
                if (model != null) {
                    footerModel = model;
                }
            } else {
                footerModel = new FooterModel();
                footerModel.setCategory(new ArrayList<>());
            }
        }
        return footerModel;
    }

    public static void flushFooterModel() {
        footerModel = null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<PageStore> getAllSupportCatesPage(SupportV2Service storeService) {
        if (pageStores == null) {
            FullPageModel.pageStores = storeService.selectSupportCateOnlyName();
        }
        return pageStores;
    }

    public static void flushSupportCates() {
        pageStores = null;
    }
}
