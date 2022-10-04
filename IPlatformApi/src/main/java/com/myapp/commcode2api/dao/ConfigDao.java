package com.myapp.commcode2api.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface ConfigDao {
    @Select("SELECT c.content FROM config c WHERE c.name=#{configName}")
    @Cacheable(cacheNames = "captchaConfig",cacheManager = "configRedisManager",unless="#result == null")
    String findConfigByName(String configName);
}
