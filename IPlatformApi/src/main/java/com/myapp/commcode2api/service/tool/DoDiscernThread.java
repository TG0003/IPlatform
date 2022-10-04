package com.myapp.commcode2api.service.tool;

import com.alibaba.fastjson.JSONObject;
import com.myapp.commcode2api.constant.CaptchaTypeEnums;
import com.myapp.commcode2api.constant.ServerConfigConstants;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.util.NetUtils;
import com.myapp.commcode2api.vo.discren.DoDiscernVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.concurrent.Semaphore;

public class DoDiscernThread extends Thread{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String res = "";
    private DoDiscernVO doDiscernVO;
    private Semaphore semaphore;
    private RedisCacheManager captchaConfRedisManager;
    public DoDiscernThread(DoDiscernVO doDiscernVO,RedisCacheManager redisCacheManager,Semaphore semaphore){
        this.doDiscernVO = doDiscernVO;
        this.captchaConfRedisManager = redisCacheManager;
        this.semaphore = semaphore;
    }
    private String accessPyServer(String path,String jsonData) throws Exception{
        String url = ServerConfigConstants.PYTHON_SERVER+path;
        return NetUtils.senJsonPost(url,jsonData);
    }
    private JSONObject getWyInitConf() throws Exception{
        RedisCache wyCache = (RedisCache) captchaConfRedisManager.getCache("wyConf");
        JSONObject conf = wyCache.get(doDiscernVO.getId(),JSONObject.class);
        if (conf == null || conf.getInteger("count") >= 200){
            //从py服务获取
            String c = accessPyServer("/wyInit",JSONObject.toJSONString(doDiscernVO));
            JSONObject retJson = JSONObject.parseObject(c);
            if(retJson.getInteger("code") == 1){
                String errMsg = "网易验证码初始化失败："+doDiscernVO.getId()+"->"+retJson.get("msg");
                retJson.put("msg",errMsg);
                res = retJson.toJSONString();
                logger.error(errMsg);
                throw new BusinessException(errMsg);
            }
            conf = retJson.getJSONObject("data");
            conf.put("count",0);
        }
        conf.put("count",conf.getInteger("count")+1);
        wyCache.put(doDiscernVO.getId(),conf);
        return conf;
    }
    @Override
    public void run(){
        try {
            semaphore.acquire();
            //网易：获取该id的初始配置
            JSONObject conf = null;
            if (CaptchaTypeEnums.WYYD.getCode().equals(doDiscernVO.getType())){
                conf = getWyInitConf();
            }
            JSONObject disObj = JSONObject.parseObject(JSONObject.toJSONString(doDiscernVO));
            disObj.put("iniWyConf",conf);
            res = accessPyServer("/discern",disObj.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }
    public String getRes(){
        return res;
    }
}
