package com.myapp.commcode2api.constant;

public enum CacheEnums {
    DIS_REDIS_MANAGER("disRedisManager",0),
    ZERO_CLEAR("zeroClear",0);
    private String name;
    private Integer code;

    CacheEnums(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
