package com.seeu.shopper.ashop.domain.footer;

import com.seeu.shopper.ashop.domain.index.Category;

import java.util.List;

/**
 * Created by neo on 23/09/2017.
 */
public class FooterModel {
    private List<Category> category;   // 底部导航栏信息

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
