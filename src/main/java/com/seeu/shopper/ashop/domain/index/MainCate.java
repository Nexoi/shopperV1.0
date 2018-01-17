package com.seeu.shopper.ashop.domain.index;

import java.util.List;


public class MainCate {
    private String cateName;
    private String pids;  // 限定 8 个以内商品的 pid string["1,3,2,6"]

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }
}

