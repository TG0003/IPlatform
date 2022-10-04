package com.myapp.commcode2api.constant;

public enum CaptchaTypeEnums {
    WYYD("WY","网易易盾"),
    GEETEST("JY","极验"),
    TX("TX","腾讯"),
    TX_MINI_APP("TX_XCX","腾讯小程序");
    private String code;
    private String name;
    private double price;
    CaptchaTypeEnums(String code,String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
