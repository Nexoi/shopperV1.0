package com.seeu.shopper.order.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_basic")
public class OrderBasic {
    @Id
    private String oid;

    /**
     * 1:已下单，未支付
     */
    private Integer status;

    private Integer uid;

    @Column(name = "user_name")
    private String userName;

    private Integer quantity;

    /**
     * 计价单位
     */
    private String unit;

    @Column(name = "origin_price")
    private BigDecimal originPrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 使用优惠券后节省价格
     */
    @Column(name = "save_price")
    private BigDecimal savePrice;

    @Column(name = "ship_price")
    private BigDecimal shipPrice;

    private BigDecimal price;

    private Integer weight;

    private String coupon;

    @Column(name = "coupon_type")
    private Integer couponType;

    /**
     * 物流方式id
     */
    @Column(name = "ship_id")
    private Integer shipId;

    @Column(name = "pay_method")
    private Integer payMethod;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支付完成时间
     */
    @Column(name = "pay_finish_time")
    private Date payFinishTime;

    /**
     * 发货开始时间
     */
    @Column(name = "ship_start_time")
    private Date shipStartTime;

    /**
     * 交易完成关闭时间
     */
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * @param oid
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * 获取1:已下单，未支付
     *
     * @return status - 1:已下单，未支付
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:已下单，未支付
     *
     * @param status 1:已下单，未支付
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取计价单位
     *
     * @return unit - 计价单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置计价单位
     *
     * @param unit 计价单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 获取最终价
     *
     * @return origin_price - 最终价
     */
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    /**
     * 设置最终价
     *
     * @param originPrice 最终价
     */
    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * @return sale_price
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 获取使用优惠券后节省价格
     *
     * @return save_price - 使用优惠券后节省价格
     */
    public BigDecimal getSavePrice() {
        return savePrice;
    }

    /**
     * 设置使用优惠券后节省价格
     *
     * @param savePrice 使用优惠券后节省价格
     */
    public void setSavePrice(BigDecimal savePrice) {
        this.savePrice = savePrice;
    }

    /**
     * @return ship_price
     */
    public BigDecimal getShipPrice() {
        return shipPrice;
    }

    /**
     * @param shipPrice
     */
    public void setShipPrice(BigDecimal shipPrice) {
        this.shipPrice = shipPrice;
    }

    /**
     * 获取原价
     *
     * @return price - 原价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置原价
     *
     * @param price 原价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return weight
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * @return coupon
     */
    public String getCoupon() {
        return coupon;
    }

    /**
     * @param coupon
     */
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    /**
     * @return coupon_type
     */
    public Integer getCouponType() {
        return couponType;
    }

    /**
     * @param couponType
     */
    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    /**
     * 获取物流方式id
     *
     * @return ship_id - 物流方式id
     */
    public Integer getShipId() {
        return shipId;
    }

    /**
     * 设置物流方式id
     *
     * @param shipId 物流方式id
     */
    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    /**
     * @return pay_method
     */
    public Integer getPayMethod() {
        return payMethod;
    }

    /**
     * @param payMethod
     */
    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
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

    /**
     * 获取支付完成时间
     *
     * @return pay_finish_time - 支付完成时间
     */
    public Date getPayFinishTime() {
        return payFinishTime;
    }

    /**
     * 设置支付完成时间
     *
     * @param payFinishTime 支付完成时间
     */
    public void setPayFinishTime(Date payFinishTime) {
        this.payFinishTime = payFinishTime;
    }

    /**
     * 获取发货开始时间
     *
     * @return ship_start_time - 发货开始时间
     */
    public Date getShipStartTime() {
        return shipStartTime;
    }

    /**
     * 设置发货开始时间
     *
     * @param shipStartTime 发货开始时间
     */
    public void setShipStartTime(Date shipStartTime) {
        this.shipStartTime = shipStartTime;
    }

    /**
     * 获取交易完成关闭时间
     *
     * @return end_time - 交易完成关闭时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置交易完成关闭时间
     *
     * @param endTime 交易完成关闭时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}