package com.myapp.commcode2api.service.impl;

import com.myapp.commcode2api.constant.CacheEnums;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
    @Qualifier(value = "discernRedisManager")
    @Autowired
    private RedisCacheManager discernRedisManager;
    @Qualifier(value = "loginRedisManager")
    @Autowired
    private RedisCacheManager loginRedisManager;
    @Qualifier(value = "createRedisManager")
    @Autowired
    private RedisCacheManager createRedisManager;
    @Qualifier(value = "otherCtlRedisManager")
    @Autowired
    private RedisCacheManager otherCtlRedisManager;
    @Qualifier(value = "configRedisManager")
    @Autowired
    private RedisCacheManager configRedisManager;

    @Override
    public void clearFail(String key, int count,Integer code) {
        if (CacheEnums.DIS_REDIS_MANAGER.getCode().compareTo(code) == 0){
            RedisCache cache = (RedisCache) discernRedisManager.getCache("discernEx");
            cache.put(key,count);
        }
    }

    @Override
    public void traceDiscernExceptionBySecret(String secret,int way) throws Exception {
        doCtrl(discernRedisManager,"discernEx",secret,"请求异常，请10分钟后重试",30,way);
    }

    @Override
    public void traceLoginFailByIp(String ip,int way) throws Exception {
        doCtrl(loginRedisManager,"loginFail",ip,"请求异常，请10分钟后重试",30,way);
    }

    @Override
    public void traceCreateByIp(String ip,int way) throws Exception {
        doCtrl(createRedisManager,"register",ip,"请求异常，请稍后重试",10,way);
    }

    @Override
    public void traceOtherOpsByIp(String ip,int way) throws Exception {
        doCtrl(otherCtlRedisManager,"opsFail",ip,"请求异常，请稍后重试",30,way);
    }

    @Override
    public void storeCaptchaCodeByOpenId(String openId, String code) {
        RedisCache cache = (RedisCache) configRedisManager.getCache("captchaCode");
        cache.put(openId,code);
    }

    @Override
    public String getCaptchaCodeByOpenId(String openId) throws Exception {
        RedisCache cache = (RedisCache) configRedisManager.getCache("captchaCode");
        String code = cache.get(openId,String.class);
        if (code == null){
            throw new BusinessException("验证码已失效");
        }
        return code;
    }

    @Override
    public void rmCaptchaCodeByOpenId(String openId) {
        RedisCache cache = (RedisCache) configRedisManager.getCache("captchaCode");
        cache.evict(openId);
    }

    private void doCtrl(RedisCacheManager cacheManager,String cacheName,String key,String errMsg,int limitCount,int way) throws Exception{
        //way 0:检查，1:设置
        RedisCache cache = (RedisCache) cacheManager.getCache(cacheName);
        Integer failCount = cache.get(key,Integer.class);
        failCount = failCount == null?0:failCount;
        if (failCount > limitCount){
            throw new BusinessException(errMsg);
        }
        if (way == 0){
            return;
        }
        failCount++;
        cache.put(key,failCount);
    }
}
