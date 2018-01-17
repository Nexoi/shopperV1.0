package com.seeu.shopper.product.model;

import javax.persistence.*;

@Table(name = "product_norm")
public class ProductNorm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer pid;

    /**
     * defalut is -1，表示为分组名字
     */
    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "norm_name")
    private String normName;

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
     * 获取defalut is -1，表示为分组名字
     *
     * @return team_id - defalut is -1，表示为分组名字
     */
    public Integer getTeamId() {
        return teamId;
    }

    /**
     * 设置defalut is -1，表示为分组名字
     *
     * @param teamId defalut is -1，表示为分组名字
     */
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    /**
     * @return norm_name
     */
    public String getNormName() {
        return normName;
    }

    /**
     * @param normName
     */
    public void setNormName(String normName) {
        this.normName = normName;
    }
}