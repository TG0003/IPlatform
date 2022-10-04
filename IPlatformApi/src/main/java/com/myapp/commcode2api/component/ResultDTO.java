package com.myapp.commcode2api.component;

public class ResultDTO<T> {
    private String message;
    private Integer code;
    private T data;
    private Boolean success;
    public ResultDTO withMessage(String message){
        this.message = message;
        return this;
    }
    public ResultDTO withCode(Integer code){
        this.code = code;
        return this;
    }
    public ResultDTO withData(T data){
        this.data = data;
        return this;
    }
    public ResultDTO withSuccess(Boolean success){
        this.success = success;
        return this;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
