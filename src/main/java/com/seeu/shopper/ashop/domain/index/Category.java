package com.seeu.shopper.ashop.domain.index;

import java.util.List;

/**
 * Created by neo on 03/09/2017.
 */
public class Category {
    private String name;
    private List<CateItem> items;
    private String imgURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CateItem> getItems() {
        return items;
    }

    public void setItems(List<CateItem> items) {
        this.items = items;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
