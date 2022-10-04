package com.myapp.commcode2api.dao;

import com.myapp.commcode2api.entity.Discern;
import com.myapp.commcode2api.vo.discren.GetDiscernVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface DiscernDao {
    // @CacheEvict(cacheNames = "discern",key = "#secret",cacheManager = "disRedisManager")
    @Insert("INSERT INTO discern(no,user_id,type,captcha_id,captcha_referer,discern_time,captcha_info,tj_id,result,result_state,discern_ip) VALUES(#{no},#{userId},#{type},#{captchaId},#{captchaReferer},#{discernTime},#{captchaInfo},#{tjId},#{result},#{resultState},#{discernIp})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer addDiscern(Discern discern);
    @Select("SELECT * FROM discern d WHERE d.no=#{no}")
    Discern findDiscernByDiscernNo(String no);
    // @Cacheable(cacheNames = "discern",key = "#secret",cacheManager = "disRedisManager",unless="#result == null")
    @Select("SELECT d.no,d.user_id,d.type,d.captcha_id,d.captcha_referer,d.discern_time,d.captcha_info,d.tj_id,d.result,d.result_state,d.discern_ip FROM discern d WHERE d.user_id=(SELECT u.id FROM user u WHERE u.secret=#{secret}) order by d.discern_time desc")
    List<GetDiscernVO> findDiscernsByUserSecret(String secret);
}
