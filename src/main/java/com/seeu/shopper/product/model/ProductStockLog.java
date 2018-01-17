package com.seeu.shopper.product.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "product_stock_log")
public class ProductStockLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer sid;

    /**
     * 0 - 入库
1 - 出库
     */
    private Boolean type;

    private Integer amount;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作管理员id
     */
    @Column(name = "op_admin_id")
    private Integer opAdminId;

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
     * 获取0 - 入库
1 - 出库
     *
     * @return type - 0 - 入库
1 - 出库
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置0 - 入库
1 - 出库
     *
     * @param type 0 - 入库
1 - 出库
     */
    public void setType(Boolean type) {
        this.type = type;
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

    /**
     * 获取操作管理员id
     *
     * @return op_admin_id - 操作管理员id
     */
    public Integer getOpAdminId() {
        return opAdminId;
    }

    /**
     * 设置操作管理员id
     *
     * @param opAdminId 操作管理员id
     */
    public void setOpAdminId(Integer opAdminId) {
        this.opAdminId = opAdminId;
    }
}