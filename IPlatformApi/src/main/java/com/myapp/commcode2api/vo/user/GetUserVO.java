package com.myapp.commcode2api.vo.user;

import java.math.BigDecimal;
import java.util.Date;

public class GetUserVO {
    private String username;
    private String secret;
    private Date createTime;
    private BigDecimal balance;
    private String type;
    private String head = "/static/head.png";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "GetUserVO{" +
                "username='" + username + '\'' +
                ", secret='" + secret + '\'' +
                ", createTime=" + createTime +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
