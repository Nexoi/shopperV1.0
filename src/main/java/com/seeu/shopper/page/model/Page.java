package com.seeu.shopper.page.model;

import javax.persistence.*;

public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "page_name")
    private String pageName;

    /**
     * json
     */
    private String config;

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
     * @return page_name
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * @param pageName
     */
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    /**
     * 获取json
     *
     * @return config - json
     */
    public String getConfig() {
        return config;
    }

    /**
     * 设置json
     *
     * @param config json
     */
    public void setConfig(String config) {
        this.config = config;
    }
}