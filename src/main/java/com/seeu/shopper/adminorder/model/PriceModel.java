package com.seeu.shopper.adminorder.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by neo on 29/08/2017.
 * <p>
 * 订单计价结果 pojo
 */
public class PriceModel {
    private Integer status;         // 计价状态
    private String oid; // 订单编号
    private List<PriceProductModel> products;// 商品信息及对应数量、价格
    private Integer quantity;       // 总共数量
    private BigDecimal originPrice; // 原总价（商品显示的市场价，永远不会纳入计价器计算价格）
    private BigDecimal salePrice;   // 售价总价（商家售价，打折、满减后的结果）

    private String cid;             // 优惠券ID
    private String coupon;          // 优惠券码
    private Integer couponType;     // 优惠券类型，1:满减；2:打折
    private BigDecimal savePrice;   // 使用优惠券后节省价格

    private BigDecimal price;       // 最终成交价格
    private BigDecimal weight;      // 总重量
    private BigDecimal weightPrice; // 物流价格

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public List<PriceProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<PriceProductModel> products) {
        this.products = products;
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getSavePrice() {
        return savePrice;
    }

    public void setSavePrice(BigDecimal savePrice) {
        this.savePrice = savePrice;
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

    public BigDecimal getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(BigDecimal weightPrice) {
        this.weightPrice = weightPrice;
    }
}