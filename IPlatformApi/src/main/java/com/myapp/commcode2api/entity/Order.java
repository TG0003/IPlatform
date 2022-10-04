package com.myapp.commcode2api.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private int id;
    private String no;
    private int userId;
    private BigDecimal payNum;
    private Date createTime;
    private String secret;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getPayNum() {
        return payNum;
    }

    public void setPayNum(BigDecimal payNum) {
        this.payNum = payNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", userId=" + userId +
                ", payNum=" + payNum +
                ", createTime=" + createTime +
                ", secret='" + secret + '\'' +
                '}';
    }
}
