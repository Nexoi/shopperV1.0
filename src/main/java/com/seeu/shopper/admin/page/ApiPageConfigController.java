package com.seeu.shopper.admin.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.admin.service.HomePageConfigService;
import com.seeu.shopper.ashop.domain.FullPageModel;
import com.seeu.shopper.ashop.domain.footer.FooterModel;
import com.seeu.shopper.ashop.domain.header.HeaderModel;
import com.seeu.shopper.ashop.domain.index.*;
import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.config.service.ConfigService;
import com.seeu.shopper.page.model.Page;
import com.seeu.shopper.page.service.PageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 20/09/2017.
 */
@RestController
@RequestMapping("/api/admin/v1/pageconfig")
public class ApiPageConfigController {
    @Resource
    PageService pageService;
    @Resource
    HomePageConfigService homePageConfigService;

    @PutMapping("/maincate")
    public Result updateItem(@RequestParam("value") String value) {
        List<MainCate> mainCates = JSONArray.parseArray(value, MainCate.class);
        IndexModel indexModel = FullPageModel.getIndexModel(pageService);
        indexModel.setMainCates(mainCates);
        return homePageConfigService.updateHomeConfig(indexModel);
    }

    @PutMapping("/subcate")
    public Result updateItem2(@RequestParam("value") String value) {
        List<SubCate> subCates = JSONArray.parseArray(value, SubCate.class);
        IndexModel indexModel = FullPageModel.getIndexModel(pageService);
        indexModel.setSubCates(subCates);
        return homePageConfigService.updateHomeConfig(indexModel);
    }

    @PutMapping("/banner")
    public Result updateItem3(@RequestParam("value") String value) {
        List<Banner> banners = JSONArray.parseArray(value, Banner.class);
        IndexModel indexModel = FullPageModel.getIndexModel(pageService);
        indexModel.setBanners(banners);
        return homePageConfigService.updateHomeConfig(indexModel);
    }

    @PutMapping("/ad")
    public Result updateItem4(@RequestParam("value") String value) {
        List<Ad> ads = JSONArray.parseArray(value, Ad.class);
        IndexModel indexModel = FullPageModel.getIndexModel(pageService);
        indexModel.setAds(ads);
        return homePageConfigService.updateHomeConfig(indexModel);
    }

    @PutMapping("/cate")
    public Result updateItem0(@RequestParam("value") String value) {
        List<Category> categories = JSONArray.parseArray(value, Category.class);
        HeaderModel headerModel = FullPageModel.getHeaderModel(pageService);
        headerModel.setCategory(categories);
        return homePageConfigService.updateHeaderConfig(headerModel);
    }

    @PutMapping("/footer")
    public Result updateItem00(@RequestParam("value") String value) {
        List<Category> categories = JSONArray.parseArray(value, Category.class);
        FooterModel footerModel = FullPageModel.getFooterModel(pageService);
        footerModel.setCategory(categories);
        return homePageConfigService.updateFooterConfig(footerModel);
    }
}
