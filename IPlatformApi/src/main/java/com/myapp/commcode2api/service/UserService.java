package com.myapp.commcode2api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.discren.DoDiscernVO;
import com.myapp.commcode2api.vo.discren.GetDiscernVO;
import com.myapp.commcode2api.vo.order.GetOrderVO;
import com.myapp.commcode2api.vo.user.GetUserVO;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    //用户注册
    GetUserVO register(String username, String password,String ip) throws Exception;
    //用户登录
    GetUserVO login(String username, String password,String ip) throws Exception;
    //用户重置密码
    String resetPassword(String newPassword,String secret) throws Exception;
    //用户重置秘钥
    GetUserVO resetSecret(String secret);
    //获取用户信息
    GetUserVO getUserInfo(String secret);
    //用户进入系统校验
    void checkEntryUser(String username,String password,String openId,String verifyCode,String ip) throws Exception;
}
