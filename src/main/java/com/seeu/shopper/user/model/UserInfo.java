package com.seeu.shopper.user.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user_info")
public class UserInfo {
    @Id
    private Integer uid;

    @Column(name = "user_name")
    private String userName;

    /**
     * 1 famale
0 male
-1 unknow
     */
    private Integer gender;

    private String email;

    private String phone;

    /**
     * -1 注销
1 正常
2 违规
4 重要客户
     */
    @Column(name = "member_status")
    private Integer memberStatus;

    private BigDecimal amount;

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
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取1 famale
0 male
-1 unknow
     *
     * @return gender - 1 famale
0 male
-1 unknow
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置1 famale
0 male
-1 unknow
     *
     * @param gender 1 famale
0 male
-1 unknow
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取-1 注销
1 正常
2 违规
4 重要客户
     *
     * @return member_status - -1 注销
1 正常
2 违规
4 重要客户
     */
    public Integer getMemberStatus() {
        return memberStatus;
    }

    /**
     * 设置-1 注销
1 正常
2 违规
4 重要客户
     *
     * @param memberStatus -1 注销
1 正常
2 违规
4 重要客户
     */
    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    /**
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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