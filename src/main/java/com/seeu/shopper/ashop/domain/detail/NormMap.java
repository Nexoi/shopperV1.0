package com.seeu.shopper.ashop.domain.detail;

import java.util.List;

/**
 * Created by neo on 03/09/2017.
 */
public class NormMap {
    private String normColName;
    private Integer normColId;// 前端拿着没用，仅供后端组合数据使用
    private List<NormItem> normItems;

    public String getNormColName() {
        return normColName;
    }

    public void setNormColName(String normColName) {
        this.normColName = normColName;
    }

    public Integer getNormColId() {
        return normColId;
    }

    public void setNormColId(Integer normColId) {
        this.normColId = normColId;
    }

    public List<NormItem> getNormItems() {
        return normItems;
    }

    public void setNormItems(List<NormItem> normItems) {
        this.normItems = normItems;
    }
}
