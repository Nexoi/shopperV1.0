package com.seeu.shopper.adminproduct.model;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by neo on 28/08/2017.
 */
public class ProductStockModel {
    @Id
    private Integer sid;

    private Integer pid;

    private Integer currentStock;

    private String normIds;

    private BigDecimal orginPrice;

    private BigDecimal price;

    private Boolean isIng;

    private Date updateDate;

    private String productName; // "iPhone7 4G xxxxxx"

    private String normValues; // [XL][Red][4.7inch]

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public String getNormIds() {
        return normIds;
    }

    public void setNormIds(String normIds) {
        this.normIds = normIds;
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

    public Boolean getIsIng() {
        return isIng;
    }

    public void setIsIng(Boolean isIng) {
        this.isIng = isIng;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNormValues() {
        return normValues;
    }

    public void setNormValues(String normValues) {
        this.normValues = normValues;
    }
}
