package com.myapp.commcode2api.service;

public interface CacheService {
    void clearFail(String key,int count,Integer code);
    void traceDiscernExceptionBySecret(String secret,int way) throws Exception;
    void traceLoginFailByIp(String ip,int way) throws Exception;
    void traceCreateByIp(String ip,int way) throws Exception;
    void traceOtherOpsByIp(String ip,int way) throws Exception;
    void storeCaptchaCodeByOpenId(String openId,String code);
    String getCaptchaCodeByOpenId(String openId) throws Exception;
    void rmCaptchaCodeByOpenId(String openId);
}
