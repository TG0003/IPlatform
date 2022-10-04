package com.myapp.commcode2api.constant;

public enum DataConfEnums {
    APP_CONFIG("appConfig","app配置"),
    HOME_CONFIG("homeConfig","首页配置"),
    PRICE_CONFIG("priceConfig","价格页配置"),
    PAY_CONFIG("payConfig","支付页配置"),
    ABOUT_ME_CONFIG("aboutMeConfig","个人中心页配置"),
    USAGE_CONFIG("usageConfig","接入页配置"),
    PAY_SUCCESS_CONFIG("paySuccessConfig","支付成功页配置");
    private String configName;
    private String desc;

    DataConfEnums(String configName, String desc) {
        this.configName = configName;
        this.desc = desc;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
