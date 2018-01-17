package com.seeu.shopper.ashop.domain.index;

import com.seeu.shopper.product.model.Product;

import java.util.List;

/**
 * Created by neo on 03/09/2017.
 */
public class CateModel {
    private String id;  // 唯一识别id，用于元素标签指定
    private String cateName;
    private String subName;  // 只限于 subcate 使用，maincate 没有
    private String imgURL;  // 只限于 subcate 使用，maincate 没有
    private String url;
    private List<Product> products;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
