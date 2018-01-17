package com.seeu.shopper.ashop.domain.index;

import java.util.List;

/**
 * Created by neo on 02/09/2017.
 */
public class IndexModel {
    private List<Category> category;    // 主分类
    private List<Banner> banners;       // 轮播牌
    private List<MainCate> mainCates;   // 分类 1
    private List<SubCate> subCates;     // 分类 2
    private List<Ad> ads;               // 广告

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public List<MainCate> getMainCates() {
        return mainCates;
    }

    public void setMainCates(List<MainCate> mainCates) {
        this.mainCates = mainCates;
    }

    public List<SubCate> getSubCates() {
        return subCates;
    }

    public void setSubCates(List<SubCate> subCates) {
        this.subCates = subCates;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
