package com.seeu.shopper.config.model;

import javax.persistence.*;

public class Config {
    /**
     * 配置信息表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

    private Integer sequence;

    private String note;

    /**
     * 获取配置信息表
     *
     * @return id - 配置信息表
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置配置信息表
     *
     * @param id 配置信息表
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return attribute_name
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * @return attribute_value
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * @param attributeValue
     */
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     * @return sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * @param sequence
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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