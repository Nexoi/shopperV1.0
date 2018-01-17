package com.seeu.shopper.ashop.domain.detail;

import java.math.BigDecimal;

/**
 * Created by neo on 05/09/2017.
 */
public class HotProduct {
    private String name;
    private Integer pid;
    private String img;
    private BigDecimal current_price;
    private BigDecimal origin_price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(BigDecimal current_price) {
        this.current_price = current_price;
    }

    public BigDecimal getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(BigDecimal origin_price) {
        this.origin_price = origin_price;
    }
}
