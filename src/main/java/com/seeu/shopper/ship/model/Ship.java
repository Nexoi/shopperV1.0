package com.seeu.shopper.ship.model;

import java.math.BigDecimal;
import javax.persistence.*;

public class Ship {
    @Id
    @Column(name = "ship_id")
    private Integer shipId;

    private String name;

    private Integer sort;

    private String note;

    private String website;

    /**
     * 一公斤后每公斤价格
     */
    @Column(name = "addi_price")
    private BigDecimal addiPrice;

    /**
     * 一公斤内价格
     */
    @Column(name = "base_price")
    private BigDecimal basePrice;

    /**
     * @return ship_id
     */
    public Integer getShipId() {
        return shipId;
    }

    /**
     * @param shipId
     */
    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 获取一公斤后每公斤价格
     *
     * @return addi_price - 一公斤后每公斤价格
     */
    public BigDecimal getAddiPrice() {
        return addiPrice;
    }

    /**
     * 设置一公斤后每公斤价格
     *
     * @param addiPrice 一公斤后每公斤价格
     */
    public void setAddiPrice(BigDecimal addiPrice) {
        this.addiPrice = addiPrice;
    }

    /**
     * 获取一公斤内价格
     *
     * @return base_price - 一公斤内价格
     */
    public BigDecimal getBasePrice() {
        return basePrice;
    }

    /**
     * 设置一公斤内价格
     *
     * @param basePrice 一公斤内价格
     */
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}