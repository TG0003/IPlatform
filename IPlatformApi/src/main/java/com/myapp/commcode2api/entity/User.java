package com.myapp.commcode2api.entity;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private int id;
    private String username;
    private String password;
    private String secret;
    private String type;
    private BigDecimal balance;
    private String valid;
    private Date createTime;
    private Date updateTime;
    private Date recentLoginTime;
    private String loginIp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getRecentLoginTime() {
        return recentLoginTime;
    }

    public void setRecentLoginTime(Date recentLoginTime) {
        this.recentLoginTime = recentLoginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", secret='" + secret + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", valid='" + valid + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", recentLoginTime=" + recentLoginTime +
                ", loginIp='" + loginIp + '\'' +
                '}';
    }
}
