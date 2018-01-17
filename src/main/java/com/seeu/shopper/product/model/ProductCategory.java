package com.seeu.shopper.product.model;

import javax.persistence.*;

@Table(name = "product_category")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * default is -1
     */
    @Column(name = "father_id")
    private Integer fatherId;

    private String name;

    private String detail;

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
     * 获取default is -1
     *
     * @return father_id - default is -1
     */
    public Integer getFatherId() {
        return fatherId;
    }

    /**
     * 设置default is -1
     *
     * @param fatherId default is -1
     */
    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
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
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}