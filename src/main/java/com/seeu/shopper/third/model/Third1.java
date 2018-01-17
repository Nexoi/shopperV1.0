package com.seeu.shopper.third.model;

import javax.persistence.*;

public class Third1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "imgURL")
    private String imgurl;

    @Column(name = "linkURL")
    private String linkurl;

    @Column(name = "md_code")
    private String MD5;

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

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
     * @return imgURL
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * @param imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * @return linkURL
     */
    public String getLinkurl() {
        return linkurl;
    }

    /**
     * @param linkurl
     */
    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}