package com.myapp.commcode2api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.commcode2api.dao.ConfigDao;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigDao configDao;
    @Override
    public String getConfig(String confName) throws Exception {
        String configContent = configDao.findConfigByName(confName);
        if (configContent == null){
            throw new BusinessException("无效配置项");
        }
        return configContent;
    }
}
