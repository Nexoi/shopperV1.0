package com.seeu.shopper.ashop.domain.index;

/**
 * Created by neo on 03/09/2017.
 */
public class Ad {
    private String imgURL;  // 图片地址
    private String url;     // 跳转链接

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
