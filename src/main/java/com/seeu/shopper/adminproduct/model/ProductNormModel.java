package com.seeu.shopper.adminproduct.model;

import java.util.List;

/**
 * Created by neo on 27/08/2017.
 */
public class ProductNormModel {
    private Integer pid;
    private String normName;
    private List<String> normValues;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNormName() {
        return normName;
    }

    public void setNormName(String normName) {
        this.normName = normName;
    }

    public List<String> getNormValues() {
        return normValues;
    }

    public void setNormValues(List<String> normValues) {
        this.normValues = normValues;
    }
}
