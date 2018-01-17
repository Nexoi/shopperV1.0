package com.seeu.shopper.adminproduct.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by neo on 27/08/2017.
 */
public class ProductPictureModel {
    private Integer pid;
    private MultipartFile picture;
    private Integer img_order;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public Integer getImg_order() {
        return img_order;
    }

    public void setImg_order(Integer img_order) {
        this.img_order = img_order;
    }
}
