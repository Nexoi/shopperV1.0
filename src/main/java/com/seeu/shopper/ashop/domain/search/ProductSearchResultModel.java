package com.seeu.shopper.ashop.domain.search;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by neo on 11/09/2017.
 */
public class ProductSearchResultModel {
    @Id
    private Integer pid;

    /**
     * 0 下架
     1 上架
     */
    private Integer status;

    private String name;

    private String subname;

    private Integer category_id;

    private BigDecimal origin_price;

    private BigDecimal current_price;

    private Boolean is_deal;

    /**
     * 促销时商品折扣，10表示不促销
     */
    private BigDecimal deal_discount;

    private Date deal_start;

    private Date deal_ddl;

    private String img;

    /**
     * 图片右上方的三角牌
     */
    private Integer banner_type;

    /**
     * kg
     */
    private BigDecimal weight;

    /**
     * list: box,minbox,dodobox
     */
    private String tag;

    private Integer click_times;

    private Integer sales;

    private String keyword;

    private Date createdate;

    private Date updatedate;

    private Integer star1_num;

    private Integer star2_num;

    private Integer star3_num;

    private Integer star4_num;

    private Integer star5_num;

    private Integer stars_num;

    private BigDecimal stars;

    /**
     * 以下是库存信息
     */
    private Integer sid;

    private Integer current_stock;

    /**
     * 规格id，逗号隔开，升序
     3,6,8,12
     */
    private String norm_ids;

    private BigDecimal orgin_price;

    private BigDecimal price;

    /**
     * 启动该库存
     */
    private Boolean is_ing;

    private Date update_date;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public BigDecimal getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(BigDecimal origin_price) {
        this.origin_price = origin_price;
    }

    public BigDecimal getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(BigDecimal current_price) {
        this.current_price = current_price;
    }

    public Boolean getIs_deal() {
        return is_deal;
    }

    public void setIs_deal(Boolean is_deal) {
        this.is_deal = is_deal;
    }

    public BigDecimal getDeal_discount() {
        return deal_discount;
    }

    public void setDeal_discount(BigDecimal deal_discount) {
        this.deal_discount = deal_discount;
    }

    public Date getDeal_start() {
        return deal_start;
    }

    public void setDeal_start(Date deal_start) {
        this.deal_start = deal_start;
    }

    public Date getDeal_ddl() {
        return deal_ddl;
    }

    public void setDeal_ddl(Date deal_ddl) {
        this.deal_ddl = deal_ddl;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(Integer banner_type) {
        this.banner_type = banner_type;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getClick_times() {
        return click_times;
    }

    public void setClick_times(Integer click_times) {
        this.click_times = click_times;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Integer getStar1_num() {
        return star1_num;
    }

    public void setStar1_num(Integer star1_num) {
        this.star1_num = star1_num;
    }

    public Integer getStar2_num() {
        return star2_num;
    }

    public void setStar2_num(Integer star2_num) {
        this.star2_num = star2_num;
    }

    public Integer getStar3_num() {
        return star3_num;
    }

    public void setStar3_num(Integer star3_num) {
        this.star3_num = star3_num;
    }

    public Integer getStar4_num() {
        return star4_num;
    }

    public void setStar4_num(Integer star4_num) {
        this.star4_num = star4_num;
    }

    public Integer getStar5_num() {
        return star5_num;
    }

    public void setStar5_num(Integer star5_num) {
        this.star5_num = star5_num;
    }

    public Integer getStars_num() {
        return stars_num;
    }

    public void setStars_num(Integer stars_num) {
        this.stars_num = stars_num;
    }

    public BigDecimal getStars() {
        return stars;
    }

    public void setStars(BigDecimal stars) {
        this.stars = stars;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getCurrent_stock() {
        return current_stock;
    }

    public void setCurrent_stock(Integer current_stock) {
        this.current_stock = current_stock;
    }

    public String getNorm_ids() {
        return norm_ids;
    }

    public void setNorm_ids(String norm_ids) {
        this.norm_ids = norm_ids;
    }

    public BigDecimal getOrgin_price() {
        return orgin_price;
    }

    public void setOrgin_price(BigDecimal orgin_price) {
        this.orgin_price = orgin_price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIs_ing() {
        return is_ing;
    }

    public void setIs_ing(Boolean is_ing) {
        this.is_ing = is_ing;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}
