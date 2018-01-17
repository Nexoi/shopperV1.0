package com.seeu.shopper.adminproduct.model;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * Created by neo on 26/08/2017.
 */
public class ProductBasicModel {
    private Integer pid;
    private MultipartFile picture;
    private Integer category;
    private String name;
    private String subname;
    private BigDecimal orginPrice;
    private BigDecimal price;
    private BigDecimal weight;
    private String tag;
    private String keyword;
    private Integer status;

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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public BigDecimal getOrginPrice() {
        return orginPrice;
    }

    public void setOrginPrice(BigDecimal orginPrice) {
        this.orginPrice = orginPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
