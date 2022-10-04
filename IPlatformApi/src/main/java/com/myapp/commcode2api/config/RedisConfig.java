package com.myapp.commcode2api.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.myapp.commcode2api.entity.Discern;
import com.myapp.commcode2api.entity.Order;
import com.myapp.commcode2api.entity.User;
import netscape.javascript.JSObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
@Configuration
public class RedisConfig {
    private static final Jackson2JsonRedisSerializer<Integer> INT_SER = new Jackson2JsonRedisSerializer<>(Integer.class);
    private static final Jackson2JsonRedisSerializer<String> STR_SER = new Jackson2JsonRedisSerializer<>(String.class);
    private static final Jackson2JsonRedisSerializer<User> USR_SER = new Jackson2JsonRedisSerializer<>(User.class);
    private static final Jackson2JsonRedisSerializer<Discern> DIS_SER = new Jackson2JsonRedisSerializer<>(Discern.class);
    private static final Jackson2JsonRedisSerializer<Order> OD_SER = new Jackson2JsonRedisSerializer<>(Order.class);
    private static final FastJsonRedisSerializer<JSONObject> JSON_SER = new FastJsonRedisSerializer<>(JSONObject.class);
    @Primary
    @Bean
    public RedisCacheManager defaultRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()).build();
    }
    @Bean
    public RedisCacheManager configRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(STR_SER)).entryTtl(Duration.ofSeconds(180))).build();
    }
    @Bean
    public RedisCacheManager userRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(12000)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(USR_SER))).build();
    }
    @Bean
    public RedisCacheManager disRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60000)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(DIS_SER))).build();
    }
    @Bean
    public RedisCacheManager orderRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60000)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(OD_SER))).build();
    }
    @Bean
    public RedisCacheManager captchaConfRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(JSON_SER))).build();
    }
    @Bean
    public RedisCacheManager discernRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(INT_SER))).build();
    }
    @Bean
    public RedisCacheManager loginRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(INT_SER))).build();
    }
    @Bean
    public RedisCacheManager createRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(100)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(INT_SER))).build();
    }
    @Bean
    public RedisCacheManager otherCtlRedisManager(RedisConnectionFactory connectionFactory){
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(3)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(INT_SER))).build();
    }
}
