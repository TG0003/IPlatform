package com.myapp.commcode2api.vo.discren;

import javax.validation.constraints.*;

public class DoDiscernVO {
    @NotBlank(message = "secret不能为空")
    private String secret;
    private String id;
    private String referer;
    @NotBlank(message = "type不能为空")
    private String type;
    private String ua;
    @NotNull(message = "tjUsername不能为空")
    private String tjUsername;
    @NotNull(message = "tjPassword不能为空")
    private String tjPassword;
    @Pattern(regexp = "^[0-9]{2,3}\\.[0-9]{2,3}\\.[0-9]{2,3}\\.[0-9]{2,3}:[0-9]{2,10}$",message = "ip格式不合法")
    private String ip;
    @Min(value = 1,message = "count最小为1")
    @Max(value = 30,message = "count最多为30")
    private Integer count;
    @Min(value = 0,message = "forSleepTime最小为0")
    @Max(value = 3,message = "forSleepTime最多为3")
    private Integer forSleepTime;
    @Min(value = 0,message = "discernDelay最小为0")
    @Max(value = 5,message = "discernDelay最多为5")
    private Integer discernDelay;
    private String extraData;

    //极验识别参数
    private String gtServer;
    private boolean otherOffset;
    private String gt;
    private String challenge;
    private String ofType;

    //腾讯识别参数
    private String aid;

    // 单次调用扣除金额
    private double subtractNum;


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getTjUsername() {
        return tjUsername;
    }

    public void setTjUsername(String tjUsername) {
        this.tjUsername = tjUsername;
    }

    public String getTjPassword() {
        return tjPassword;
    }

    public void setTjPassword(String tjPassword) {
        this.tjPassword = tjPassword;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getForSleepTime() {
        return forSleepTime;
    }

    public void setForSleepTime(Integer forSleepTime) {
        this.forSleepTime = forSleepTime;
    }

    public Integer getDiscernDelay() {
        return discernDelay;
    }

    public void setDiscernDelay(Integer discernDelay) {
        this.discernDelay = discernDelay;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getGtServer() {
        return gtServer;
    }

    public void setGtServer(String gtServer) {
        this.gtServer = gtServer;
    }

    public boolean isOtherOffset() {
        return otherOffset;
    }

    public void setOtherOffset(boolean otherOffset) {
        this.otherOffset = otherOffset;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getOfType() {
        return ofType;
    }

    public void setOfType(String ofType) {
        this.ofType = ofType;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public double getSubtractNum() {
        return subtractNum;
    }

    public void setSubtractNum(double subtractNum) {
        this.subtractNum = subtractNum;
    }

    @Override
    public String toString() {
        return "DoDiscernVO{" +
                "secret='" + secret + '\'' +
                ", id='" + id + '\'' +
                ", referer='" + referer + '\'' +
                ", type='" + type + '\'' +
                ", ua='" + ua + '\'' +
                ", tjUsername='" + tjUsername + '\'' +
                ", tjPassword='" + tjPassword + '\'' +
                ", ip='" + ip + '\'' +
                ", count=" + count +
                ", forSleepTime=" + forSleepTime +
                ", discernDelay=" + discernDelay +
                ", extraData='" + extraData + '\'' +
                ", gtServer='" + gtServer + '\'' +
                ", otherOffset=" + otherOffset +
                ", gt='" + gt + '\'' +
                ", challenge='" + challenge + '\'' +
                ", ofType='" + ofType + '\'' +
                ", aid='" + aid + '\'' +
                ", subtractNum=" + subtractNum +
                '}';
    }
}
