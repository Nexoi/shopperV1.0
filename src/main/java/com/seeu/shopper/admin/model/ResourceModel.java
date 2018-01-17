package com.seeu.shopper.admin.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by neo on 23/09/2017.
 */
public class ResourceModel {
    private MultipartFile resource;
    private String name;
    /**
     * 1 图片
     2 其余文件
     */
    private Integer type;

    public MultipartFile getResource() {
        return resource;
    }

    public void setResource(MultipartFile resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
