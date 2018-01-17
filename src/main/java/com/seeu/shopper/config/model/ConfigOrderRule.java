package com.seeu.shopper.config.model;

import javax.persistence.*;

@Table(name = "config_order_rule")
public class ConfigOrderRule {
    @Id
    private Integer status;

    /**
     * 0 表示终止，无法走到下一步
     */
    @Column(name = "next_status")
    private String nextStatus;

    private String note;

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取0 表示终止，无法走到下一步
     *
     * @return next_status - 0 表示终止，无法走到下一步
     */
    public String getNextStatus() {
        return nextStatus;
    }

    /**
     * 设置0 表示终止，无法走到下一步
     *
     * @param nextStatus 0 表示终止，无法走到下一步
     */
    public void setNextStatus(String nextStatus) {
        this.nextStatus = nextStatus;
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
}