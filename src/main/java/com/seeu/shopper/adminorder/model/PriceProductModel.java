package com.seeu.shopper.adminorder.model;

import com.seeu.shopper.product.model.Product;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 29/08/2017.
 */
public class PriceProductModel {
    private Integer pid;
    private Product product;        // 商品基本信息
    // 以下信息都是累和信息
    private Integer quantity;       // 数量
    private String normValues;      // 规格ids [34_67_72]
    private Map<Integer, String> norms;     // 规格名字
    private Integer currentStock;   // 当前库存余量
    private BigDecimal originPrice; // 原价
    private BigDecimal salePrice;   // 售价
    @Size(min = 0, max = 1)
    private BigDecimal discount;    // 折扣： 7 折 表示 30% discount
    private BigDecimal price;       // 最终价格

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getNormValues() {
        return normValues;
    }

    public void setNormValues(String normValues) {
        this.normValues = normValues;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public Map<Integer, String> getNorms() {
        return norms;
    }

    public void setNorms(Map<Integer, String> norms) {
        this.norms = norms;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}
