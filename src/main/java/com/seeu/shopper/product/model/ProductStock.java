package com.seeu.shopper.product.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "product_stock")
public class ProductStock {
    @Id
    private Integer sid;

    private Integer pid;

    /**
     * 库存
     */
    @Column(name = "current_stock")
    private Integer currentStock;

    /**
     * 规格id，逗号隔开，升序
3,6,8,12
     */
    @Column(name = "norm_ids")
    private String normIds;

    @Column(name = "orgin_price")
    private BigDecimal orginPrice;

    private BigDecimal price;

    /**
     * 启动该库存
     */
    @Column(name = "is_ing")
    private Boolean isIng;

    @Column(name = "update_date")
    private Date updateDate;

    /**
     * @return sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取库存
     *
     * @return current_stock - 库存
     */
    public Integer getCurrentStock() {
        return currentStock;
    }

    /**
     * 设置库存
     *
     * @param currentStock 库存
     */
    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    /**
     * 获取规格id，逗号隔开，升序
3,6,8,12
     *
     * @return norm_ids - 规格id，逗号隔开，升序
3,6,8,12
     */
    public String getNormIds() {
        return normIds;
    }

    /**
     * 设置规格id，逗号隔开，升序
3,6,8,12
     *
     * @param normIds 规格id，逗号隔开，升序
3,6,8,12
     */
    public void setNormIds(String normIds) {
        this.normIds = normIds;
    }

    /**
     * @return orgin_price
     */
    public BigDecimal getOrginPrice() {
        return orginPrice;
    }

    /**
     * @param orginPrice
     */
    public void setOrginPrice(BigDecimal orginPrice) {
        this.orginPrice = orginPrice;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取启动该库存
     *
     * @return is_ing - 启动该库存
     */
    public Boolean getIsIng() {
        return isIng;
    }

    /**
     * 设置启动该库存
     *
     * @param isIng 启动该库存
     */
    public void setIsIng(Boolean isIng) {
        this.isIng = isIng;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}