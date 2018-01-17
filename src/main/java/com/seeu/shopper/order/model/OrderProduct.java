package com.seeu.shopper.order.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String oid;

    private Integer pid;

    private String norms;

    private String name;

    @Column(name = "origin_price")
    private BigDecimal originPrice;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    private Integer amount;

    @Column(name = "is_deal")
    private Boolean isDeal;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * @return norms
     */
    public String getNorms() {
        return norms;
    }

    /**
     * @param norms
     */
    public void setNorms(String norms) {
        this.norms = norms;
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
     * @return origin_price
     */
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    /**
     * @param originPrice
     */
    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * @return current_price
     */
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @param currentPrice
     */
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
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
     * @return is_deal
     */
    public Boolean getIsDeal() {
        return isDeal;
    }

    /**
     * @param isDeal
     */
    public void setIsDeal(Boolean isDeal) {
        this.isDeal = isDeal;
    }
}