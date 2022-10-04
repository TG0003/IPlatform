package com.myapp.commcode2api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.commcode2api.constant.DataConfEnums;
import com.myapp.commcode2api.dao.ConfigDao;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private ConfigDao configDao;
    @Override
    public double getCaptchaPriceByType(String type) throws BusinessException {
        String res = configDao.findConfigByName(DataConfEnums.PRICE_CONFIG.getConfigName());
        JSONObject captchaPriceConfig = JSONObject.parseObject(res);
        JSONArray captchaArr = captchaPriceConfig.getJSONArray("captchaTypeList");
        double price = 0;
        for (int i = 0;i < captchaArr.size();i++){
            JSONObject jsonObject = captchaArr.getJSONObject(i);
            String typeId = jsonObject.getString("typeID");
            if (type.equals(typeId)){
                price = Double.parseDouble(jsonObject.getString("price").replace("点",""));
                break;
            }

        }
        if (price <= 0){
            throw new BusinessException("无效验证码类型");
        }
        return price;
    }
}
