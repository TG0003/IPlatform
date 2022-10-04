package com.myapp.commcode2api.dao;

import com.myapp.commcode2api.entity.Order;
import com.myapp.commcode2api.vo.order.GetOrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface OrderDao {
    // @CacheEvict(cacheNames = "order",key = "#secret",cacheManager = "orderRedisManager")
    @Insert("INSERT INTO `order`(no,user_id,pay_num,create_time) VALUES(#{no},#{userId},#{payNum},#{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer addOrder(Order order);
    // @Cacheable(cacheNames = "order",key = "#secret",cacheManager = "orderRedisManager",unless="#result == null")
    @Select("SELECT o.no,o.pay_num,o.create_time FROM `order` o WHERE o.user_id=(SELECT u.id FROM user u WHERE u.secret=#{secret}) order by o.create_time desc")
    List<GetOrderVO> findOrdersByUserSecret(String secret);
    @Select("SELECT * FROM `order` o WHERE o.id=#{id}")
    Order findOrderByOrderId(Integer id);
    @Select("SELECT * FROM `order` o WHERE o.no=#{no}")
    Order findOrderByOrderNo(String no);
}
