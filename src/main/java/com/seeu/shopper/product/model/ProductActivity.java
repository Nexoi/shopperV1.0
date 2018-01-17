package com.seeu.shopper.product.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "product_activity")
public class ProductActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer aid;

    private Integer pid;

    private Integer subpid;

    /**
     * 1 捆绑销售，以主商品为总价
2 捆绑打折，总和价格打折
3 单品降价卖
     */
    private Integer type;

    /**
     * if type = 2
     */
    private BigDecimal discount;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return aid
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * @param aid
     */
    public void setAid(Integer aid) {
        this.aid = aid;
    }

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
     * @return subpid
     */
    public Integer getSubpid() {
        return subpid;
    }

    /**
     * @param subpid
     */
    public void setSubpid(Integer subpid) {
        this.subpid = subpid;
    }

    /**
     * 获取1 捆绑销售，以主商品为总价
2 捆绑打折，总和价格打折
3 单品降价卖
     *
     * @return type - 1 捆绑销售，以主商品为总价
2 捆绑打折，总和价格打折
3 单品降价卖
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1 捆绑销售，以主商品为总价
2 捆绑打折，总和价格打折
3 单品降价卖
     *
     * @param type 1 捆绑销售，以主商品为总价
2 捆绑打折，总和价格打折
3 单品降价卖
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取if type = 2
     *
     * @return discount - if type = 2
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 设置if type = 2
     *
     * @param discount if type = 2
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}