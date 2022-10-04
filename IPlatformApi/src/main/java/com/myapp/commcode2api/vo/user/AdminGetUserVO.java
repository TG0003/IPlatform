package com.myapp.commcode2api.vo.user;

import java.util.Date;

public class AdminGetUserVO extends GetUserVO{
    private String valid;
    private Date updateTime;
    private Date recentLoginTime;
    private String loginIp;

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
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
        return "AdminGetUserVO{" +
                "valid='" + valid + '\'' +
                ", updateTime=" + updateTime +
                ", recentLoginTime=" + recentLoginTime +
                ", loginIp='" + loginIp + '\'' +
                '}';
    }
}
