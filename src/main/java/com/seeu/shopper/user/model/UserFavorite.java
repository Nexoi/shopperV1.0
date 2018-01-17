package com.seeu.shopper.user.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_favorite")
public class UserFavorite {
    @Id
    private Integer uid;

    private Integer pid;

    @Column(name = "member_status")
    private Integer memberStatus;

    @Column(name = "create_date")
    private Date createDate;

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
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
     * @return member_status
     */
    public Integer getMemberStatus() {
        return memberStatus;
    }

    /**
     * @param memberStatus
     */
    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}