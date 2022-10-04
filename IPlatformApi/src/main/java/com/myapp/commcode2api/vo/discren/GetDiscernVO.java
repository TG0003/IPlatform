package com.myapp.commcode2api.vo.discren;

import java.util.Date;

public class GetDiscernVO {
    private String no;
    private int userId;
    private String type;
    private String captchaId;
    private String captchaReferer;
    private Date discernTime;
    private String captchaInfo;
    private String tjId;
    private String result;
    private String resultState;
    private String discernIp;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaReferer() {
        return captchaReferer;
    }

    public void setCaptchaReferer(String captchaReferer) {
        this.captchaReferer = captchaReferer;
    }

    public Date getDiscernTime() {
        return discernTime;
    }

    public void setDiscernTime(Date discernTime) {
        this.discernTime = discernTime;
    }

    public String getCaptchaInfo() {
        return captchaInfo;
    }

    public void setCaptchaInfo(String captchaInfo) {
        this.captchaInfo = captchaInfo;
    }

    public String getTjId() {
        return tjId;
    }

    public void setTjId(String tjId) {
        this.tjId = tjId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultState() {
        return resultState;
    }

    public void setResultState(String resultState) {
        this.resultState = resultState;
    }

    public String getDiscernIp() {
        return discernIp;
    }

    public void setDiscernIp(String discernIp) {
        this.discernIp = discernIp;
    }

    @Override
    public String toString() {
        return "GetDiscernVO{" +
                "no='" + no + '\'' +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", captchaId='" + captchaId + '\'' +
                ", captchaReferer='" + captchaReferer + '\'' +
                ", discernTime=" + discernTime +
                ", captchaInfo='" + captchaInfo + '\'' +
                ", tjId='" + tjId + '\'' +
                ", result='" + result + '\'' +
                ", resultState='" + resultState + '\'' +
                ", discernIp='" + discernIp + '\'' +
                '}';
    }
}
