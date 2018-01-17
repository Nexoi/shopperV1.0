//package com.seeu.shopper.ashop.mainpage;
//
//import com.seeu.core.Result;
//import com.seeu.core.ResultGenerator;
//import com.seeu.shopper.ashop.domain.index.*;
//import com.seeu.shopper.ashop.service.CateSearchService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by neo on 03/09/2017.
// */
//@RestController
//public class ApiTest {
//
//    @Resource
//    CateSearchService cateSearchService;
//
//    @GetMapping("/api/search")
//    public Result getSearch(
//            @RequestParam(value = "cate",required = false) Integer cate,
//            @RequestParam(value = "wd",required = false) String keyword,
//            @RequestParam("sort") Integer sortRule,
//            @RequestParam("priceCondition") Boolean priceCondition,
//            @RequestParam("min") BigDecimal min,
//            @RequestParam("max") BigDecimal max
//    ) {
//        return ResultGenerator.genSuccessResult(cateSearchService.searchPros(cate, keyword, sortRule, priceCondition, min, max));
//    }
//
//
//    @GetMapping("/api/index")
//    public Result getIndexPage() {
//        List<Ad> ads = new ArrayList<>();
//        List<Banner> banners = new ArrayList<>();
//        List<Category> categories = new ArrayList<>();
//        List<MainCate> mainCates = new ArrayList<>();
//        List<SubCate> subCates = new ArrayList<>();
//
//        Ad ad1 = new Ad();
//        ad1.setImgURL("https://image4.geekbuying.com/channel/0-2016722152541bwv.jpg");
//        ad1.setUrl("http://promotion.geekbuying.com/promotion/homtom_brand_fall_sale?pmrm=home-top-big-banner");
//        Ad ad2 = new Ad();
//        ad2.setImgURL("https://image4.geekbuying.com/channel/0-2017721154555couponpage%20(2).jpg");
//        ad2.setUrl("http://promotion.geekbuying.com/promotion/top_brand_deals?pmrm=home-top-big-banner");
//        Ad ad3 = new Ad();
//        ad3.setImgURL("https://image4.geekbuying.com/channel/0-20178515525as.jpg");
//        ad3.setUrl("http://promotion.geekbuying.com/promotion/splendid_gimbals_sale?pmrm=home-top-big-banner");
//        Ad ad4 = new Ad();
//        ad4.setImgURL("https://image4.geekbuying.com/channel/0-20176114626redminote4banner.jpg");
//        ad4.setUrl("http://promotion.geekbuying.com/promotion/autumn_tablet_sale?pmrm=home-top-big-banner");
//        Ad ad5 = new Ad();
//        ad5.setImgURL("https://image4.geekbuying.com/channel/0-201732713537banner.jpg");
//        ad5.setUrl("http://promotion.geekbuying.com/promotion/autumn_essentials_deals?pmrm=home-top-big-banner");
//        ads.add(ad1);
//        ads.add(ad2);
//        ads.add(ad3);
//        ads.add(ad4);
//        ads.add(ad5);
//
//        Banner Banner1 = new Banner();
//        Banner1.setImgURL("https://content1.geekbuying.com/banner/20170828/banner2017828172714zhengda.jpg");
//        Banner1.setUrl("http://promotion.geekbuying.com/promotion/homtom_brand_fall_sale?pmrm=home-top-big-banner");
//        Banner Banner2 = new Banner();
//        Banner2.setImgURL("https://content1.geekbuying.com/banner/20170831/banner201783117495fallbrand.jpg");
//        Banner2.setUrl("http://promotion.geekbuying.com/promotion/top_brand_deals?pmrm=home-top-big-banner");
//        Banner Banner3 = new Banner();
//        Banner3.setImgURL("https://content1.geekbuying.com/banner/20170830/banner2017830145850gimbals.jpg");
//        Banner3.setUrl("http://promotion.geekbuying.com/promotion/splendid_gimbals_sale?pmrm=home-top-big-banner");
//        Banner Banner4 = new Banner();
//        Banner4.setImgURL("https://content1.geekbuying.com/banner/20170829/banner201782995817laptops.jpg");
//        Banner4.setUrl("http://promotion.geekbuying.com/promotion/autumn_tablet_sale?pmrm=home-top-big-banner");
//        Banner Banner5 = new Banner();
//        Banner5.setImgURL("https://content1.geekbuying.com/banner/20170831/banner201783118654home.jpg");
//        Banner5.setUrl("http://promotion.geekbuying.com/promotion/autumn_essentials_deals?pmrm=home-top-big-banner");
//        banners.add(Banner1);
//        banners.add(Banner2);
//        banners.add(Banner3);
//        banners.add(Banner4);
//        banners.add(Banner5);
//
//
//        Category category2 = new Category();
//        CateItem item1 = new CateItem();
//        item1.setUrl("https://www.geekbuying.com/category/Windows-Tablets-1147/");
//        item1.setName("Windows 10 Tablets");
//        CateItem item2 = new CateItem();
//        item2.setUrl("https://www.geekbuying.com/category/Windows-Tablets-1147/");
//        item2.setName("PPA 45");
//        CateItem item3 = new CateItem();
//        item3.setUrl("https://www.geekbuying.com/category/Windows-Tablets-1147/");
//        item3.setName("iOS 9 devices");
//        List<CateItem> cateItems = new ArrayList<>();
//        cateItems.add(item1);
//        cateItems.add(item2);
//        cateItems.add(item3);
//        category2.setItems(cateItems);
//        category2.setName("VRXU");
//        category2.setImgURL("https://image4.geekbuying.com/channel/0-2017720154043vorke%20v1%20plus.png");
//
//
//        Category category1 = new Category();
//        CateItem item11 = new CateItem();
//        item11.setUrl("https://www.geekbuying.com/category/Windows-Tablets-1147/");
//        item11.setName("XP 10 System");
//        CateItem item21 = new CateItem();
//        item21.setUrl("https://www.geekbuying.com/category/Windows-Tablets-1147/");
//        item21.setName("Android 7");
//        CateItem item31 = new CateItem();
//        item31.setUrl("https://www.geekbuying.com/category/Windows-Tablets-1147/");
//        item31.setName("iOS 10 devices");
//        List<CateItem> cateItems1 = new ArrayList<>();
//        cateItems1.add(item11);
//        cateItems1.add(item21);
//        cateItems1.add(item31);
//        category1.setItems(cateItems1);
//        category1.setName("Lighting");
//        category1.setImgURL("https://image4.geekbuying.com/channel/0-201733014934yi%204k%20plus.png");
//        categories.add(category1);
//        categories.add(category2);
//
//        MainCate mainCate1 = new MainCate();
//        mainCate1.setCateName("Best Selling");
//        mainCate1.setPids("1,4,6,8,17,19");
//        MainCate mainCate2 = new MainCate();
//        mainCate2.setCateName("New & Hot");
//        mainCate2.setPids("1,17,4,8,19,6");
//        MainCate mainCate3 = new MainCate();
//        mainCate3.setCateName("Nice Tips");
//        mainCate3.setPids("17,6,19,8,1,4");
//        MainCate mainCate4 = new MainCate();
//        mainCate4.setCateName("others");
//        mainCate4.setPids("17,6,19,8,1,4");
//        mainCates.add(mainCate1);
//        mainCates.add(mainCate2);
//        mainCates.add(mainCate3);
//        mainCates.add(mainCate4);
//
//        SubCate SubCate1 = new SubCate();
//        SubCate1.setCateName("TV Box");
//        SubCate1.setPids("1,4,6");
//        SubCate SubCate2 = new SubCate();
//        SubCate2.setCateName("Flying KeyBoard");
//        SubCate2.setPids("8,19,17");
//        SubCate SubCate3 = new SubCate();
//        SubCate3.setCateName("Pretty");
//        SubCate3.setPids("17,6,19");
//        SubCate SubCate4 = new SubCate();
//        SubCate4.setCateName("others");
//        SubCate4.setPids("8,1,4");
//        subCates.add(SubCate1);
//        subCates.add(SubCate2);
//        subCates.add(SubCate3);
//        subCates.add(SubCate4);
//
//
//        IndexModel model = new IndexModel();
//        model.setAds(ads);
//        model.setBanners(banners);
//        model.setCategory(categories);
//        model.setMainCates(mainCates);
//        model.setSubCates(subCates);
//
//        return ResultGenerator.genSuccessResult(model);
//    }
//}
