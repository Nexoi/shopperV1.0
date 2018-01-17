package com.seeu.shopper.adminproduct.model;

import com.seeu.shopper.product.model.ProductCategory;

import java.util.List;

/**
 * Created by neo on 26/08/2017.
 */
public class CategoryItem {
    private ProductCategory data;// 当前结点数据
    private List<CategoryItem> child;    // 该结点下的子类别数据

    public ProductCategory getData() {
        return data;
    }

    public void setData(ProductCategory data) {
        this.data = data;
    }

    public List<CategoryItem> getChild() {
        return child;
    }

    public void setChild(List<CategoryItem> child) {
        this.child = child;
    }
}
