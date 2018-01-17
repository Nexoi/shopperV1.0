package com.seeu.shopper.support.model;

import java.util.Date;
import javax.persistence.*;

public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cateID")
    private Integer cateid;

    private String title;

    @Column(name = "img_href")
    private String imgHref;

    private String src;

    private Date updatetime;

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
     * @return cateID
     */
    public Integer getCateid() {
        return cateid;
    }

    /**
     * @param cateid
     */
    public void setCateid(Integer cateid) {
        this.cateid = cateid;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return img_href
     */
    public String getImgHref() {
        return imgHref;
    }

    /**
     * @param imgHref
     */
    public void setImgHref(String imgHref) {
        this.imgHref = imgHref;
    }

    /**
     * @return src
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return updatetime
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}