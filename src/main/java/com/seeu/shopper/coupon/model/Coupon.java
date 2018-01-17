package com.seeu.shopper.coupon.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Coupon {
    @Id
    private String cid;

    private String name;

    private String img;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "start_price")
    private BigDecimal startPrice;

    /**
     * 1 满减
2 打折
     */
    private Integer type;

    /**
     * 打折
     */
    private BigDecimal discount;

    /**
     * 满减额
     */
    private BigDecimal disprice;

    @Column(name = "is_available")
    private Boolean isAvailable;

    private Integer rest;

    private Integer amount;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(String cid) {
        this.cid = cid;
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
     * @return img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return start_price
     */
    public BigDecimal getStartPrice() {
        return startPrice;
    }

    /**
     * @param startPrice
     */
    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    /**
     * 获取1 满减
2 打折
     *
     * @return type - 1 满减
2 打折
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1 满减
2 打折
     *
     * @param type 1 满减
2 打折
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取打折
     *
     * @return discount - 打折
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 设置打折
     *
     * @param discount 打折
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 获取满减额
     *
     * @return disprice - 满减额
     */
    public BigDecimal getDisprice() {
        return disprice;
    }

    /**
     * 设置满减额
     *
     * @param disprice 满减额
     */
    public void setDisprice(BigDecimal disprice) {
        this.disprice = disprice;
    }

    /**
     * @return is_available
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * @param isAvailable
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * @return rest
     */
    public Integer getRest() {
        return rest;
    }

    /**
     * @param rest
     */
    public void setRest(Integer rest) {
        this.rest = rest;
    }

    /**
     * @return amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}