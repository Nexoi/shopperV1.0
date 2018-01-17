package com.seeu.shopper.adminorder.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by neo on 29/08/2017.
 *
 * 用户上传商品模型；包含：商品pid 及其对应数量
 */
public class ProductQuantityModelU {
    @Min(0)
    private Integer pid;        // pid
    @NotNull
    private String normValues;  // 规格信息
    @Min(0)
    private Integer quantity;   // 数量

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNormValues() {
        return normValues;
    }

    public void setNormValues(String normValues) {
        this.normValues = normValues;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
