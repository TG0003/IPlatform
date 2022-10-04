package com.myapp.commcode2api.dao;

import com.myapp.commcode2api.entity.Discern;
import com.myapp.commcode2api.entity.Order;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.vo.discren.GetDiscernVO;
import com.myapp.commcode2api.vo.order.GetOrderVO;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserDao {
    @Insert("INSERT INTO user(username,password,type,secret,balance,valid,create_time,update_time,recent_login_time,login_ip) VALUES(#{username},#{password},#{type},#{secret},#{balance},#{valid},#{createTime},#{updateTime},#{recentLoginTime},#{loginIp})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer add(User user);
    @Select("SELECT * FROM user u WHERE u.id=#{id}")
    User findUserById(Integer id);
    @Select("SELECT * FROM user u WHERE u.secret=#{secret}")
    @Cacheable(cacheNames = "user",key = "#secret",cacheManager = "userRedisManager",unless="#result == null")
    User findUserBySecret(String secret);
    @Select("SELECT * FROM user u WHERE u.username=#{username}")
    User findUserByUsername(String username);
    @Update("UPDATE user u SET u.balance=u.balance-#{subtractBalance} WHERE u.secret=#{secret}")
    @CacheEvict(cacheNames = "user",key = "#secret",cacheManager = "userRedisManager")
    Integer updateBalanceBySecret(BigDecimal subtractBalance, String secret);
    @Update("UPDATE user u SET u.login_ip=#{ip} WHERE u.id=#{id}")
    Integer updateLoginIp(String ip,Integer id);
    @Update("UPDATE user u SET u.secret=#{newSecret} WHERE u.secret=#{secret}")
    @CacheEvict(cacheNames = "user",key = "#secret",cacheManager = "userRedisManager")
    Integer updateSecret(String newSecret,String secret);
}
