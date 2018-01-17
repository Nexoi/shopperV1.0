package com.seeu.shopper.ashop.domain.header;

import com.seeu.shopper.ashop.domain.index.Category;

import java.util.List;

/**
 * Created by neo on 03/09/2017.
 *
 * 可以继续扩容增加可制定化功能
 */
public class HeaderModel {
    private List<Category> category;    // 主分类

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
