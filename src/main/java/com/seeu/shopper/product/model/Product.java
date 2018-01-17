package com.seeu.shopper.product.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Product {
    @Id
    private Integer pid;

    /**
     * 0 下架
1 上架
     */
    private Integer status;

    private String name;

    private String subname;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "origin_price")
    private BigDecimal originPrice;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @Column(name = "is_deal")
    private Boolean isDeal;

    /**
     * 促销时商品折扣，10表示不促销
     */
    @Column(name = "deal_discount")
    private BigDecimal dealDiscount;

    @Column(name = "deal_start")
    private Date dealStart;

    @Column(name = "deal_ddl")
    private Date dealDdl;

    private String img;

    /**
     * 图片右上方的三角牌
     */
    @Column(name = "banner_type")
    private Integer bannerType;

    /**
     * kg
     */
    private BigDecimal weight;

    /**
     * list: box,minbox,dodobox
     */
    private String tag;

    @Column(name = "click_times")
    private Integer clickTimes;

    private Integer sales;

    private String keyword;

    private Date createdate;

    private Date updatedate;

    @Column(name = "star1_num")
    private Integer star1Num;

    @Column(name = "star2_num")
    private Integer star2Num;

    @Column(name = "star3_num")
    private Integer star3Num;

    @Column(name = "star4_num")
    private Integer star4Num;

    @Column(name = "star5_num")
    private Integer star5Num;

    @Column(name = "stars_num")
    private Integer starsNum;

    private BigDecimal stars;

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取0 下架
1 上架
     *
     * @return status - 0 下架
1 上架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 下架
1 上架
     *
     * @param status 0 下架
1 上架
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * @return subname
     */
    public String getSubname() {
        return subname;
    }

    /**
     * @param subname
     */
    public void setSubname(String subname) {
        this.subname = subname;
    }

    /**
     * @return category_id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return origin_price
     */
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    /**
     * @param originPrice
     */
    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * @return current_price
     */
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @param currentPrice
     */
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * @return is_deal
     */
    public Boolean getIsDeal() {
        return isDeal;
    }

    /**
     * @param isDeal
     */
    public void setIsDeal(Boolean isDeal) {
        this.isDeal = isDeal;
    }

    /**
     * 获取促销时商品折扣，10表示不促销
     *
     * @return deal_discount - 促销时商品折扣，10表示不促销
     */
    public BigDecimal getDealDiscount() {
        return dealDiscount;
    }

    /**
     * 设置促销时商品折扣，10表示不促销
     *
     * @param dealDiscount 促销时商品折扣，10表示不促销
     */
    public void setDealDiscount(BigDecimal dealDiscount) {
        this.dealDiscount = dealDiscount;
    }

    /**
     * @return deal_start
     */
    public Date getDealStart() {
        return dealStart;
    }

    /**
     * @param dealStart
     */
    public void setDealStart(Date dealStart) {
        this.dealStart = dealStart;
    }

    /**
     * @return deal_ddl
     */
    public Date getDealDdl() {
        return dealDdl;
    }

    /**
     * @param dealDdl
     */
    public void setDealDdl(Date dealDdl) {
        this.dealDdl = dealDdl;
    }

    /**
     * @return img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取图片右上方的三角牌
     *
     * @return banner_type - 图片右上方的三角牌
     */
    public Integer getBannerType() {
        return bannerType;
    }

    /**
     * 设置图片右上方的三角牌
     *
     * @param bannerType 图片右上方的三角牌
     */
    public void setBannerType(Integer bannerType) {
        this.bannerType = bannerType;
    }

    /**
     * 获取kg
     *
     * @return weight - kg
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置kg
     *
     * @param weight kg
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取list: box,minbox,dodobox
     *
     * @return tag - list: box,minbox,dodobox
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置list: box,minbox,dodobox
     *
     * @param tag list: box,minbox,dodobox
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return click_times
     */
    public Integer getClickTimes() {
        return clickTimes;
    }

    /**
     * @param clickTimes
     */
    public void setClickTimes(Integer clickTimes) {
        this.clickTimes = clickTimes;
    }

    /**
     * @return sales
     */
    public Integer getSales() {
        return sales;
    }

    /**
     * @param sales
     */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /**
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return createdate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * @return updatedate
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * @param updatedate
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * @return star1_num
     */
    public Integer getStar1Num() {
        return star1Num;
    }

    /**
     * @param star1Num
     */
    public void setStar1Num(Integer star1Num) {
        this.star1Num = star1Num;
    }

    /**
     * @return star2_num
     */
    public Integer getStar2Num() {
        return star2Num;
    }

    /**
     * @param star2Num
     */
    public void setStar2Num(Integer star2Num) {
        this.star2Num = star2Num;
    }

    /**
     * @return star3_num
     */
    public Integer getStar3Num() {
        return star3Num;
    }

    /**
     * @param star3Num
     */
    public void setStar3Num(Integer star3Num) {
        this.star3Num = star3Num;
    }

    /**
     * @return star4_num
     */
    public Integer getStar4Num() {
        return star4Num;
    }

    /**
     * @param star4Num
     */
    public void setStar4Num(Integer star4Num) {
        this.star4Num = star4Num;
    }

    /**
     * @return star5_num
     */
    public Integer getStar5Num() {
        return star5Num;
    }

    /**
     * @param star5Num
     */
    public void setStar5Num(Integer star5Num) {
        this.star5Num = star5Num;
    }

    /**
     * @return stars_num
     */
    public Integer getStarsNum() {
        return starsNum;
    }

    /**
     * @param starsNum
     */
    public void setStarsNum(Integer starsNum) {
        this.starsNum = starsNum;
    }

    /**
     * @return stars
     */
    public BigDecimal getStars() {
        return stars;
    }

    /**
     * @param stars
     */
    public void setStars(BigDecimal stars) {
        this.stars = stars;
    }
}