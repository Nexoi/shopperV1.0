package com.seeu.shopper.order.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "order_ship")
public class OrderShip {
    @Id
    private String oid;

    @Column(name = "ship_name")
    private String shipName;

    @Column(name = "ship_note")
    private String shipNote;

    private BigDecimal price;

    /**
     * 快递单号
     */
    @Column(name = "ship_code")
    private String shipCode;

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
     * @return ship_name
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * @param shipName
     */
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    /**
     * @return ship_note
     */
    public String getShipNote() {
        return shipNote;
    }

    /**
     * @param shipNote
     */
    public void setShipNote(String shipNote) {
        this.shipNote = shipNote;
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
     * 获取快递单号
     *
     * @return ship_code - 快递单号
     */
    public String getShipCode() {
        return shipCode;
    }

    /**
     * 设置快递单号
     *
     * @param shipCode 快递单号
     */
    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }
}