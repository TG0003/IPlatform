package com.myapp.commcode2api.constant;

public enum ErrorCodeEnums {
    SUCCESS(200,true,"成功"),
    FAIL(500,false,"失败");
    private int code;
    private boolean success;
    private String msg;
    ErrorCodeEnums(int code,boolean success,String msg){
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
