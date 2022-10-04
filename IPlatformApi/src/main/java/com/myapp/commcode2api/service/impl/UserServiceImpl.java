package com.myapp.commcode2api.service.impl;

import com.myapp.commcode2api.constant.*;
import com.myapp.commcode2api.dao.UserDao;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.CacheService;
import com.myapp.commcode2api.service.UserService;
import com.myapp.commcode2api.util.ServiceUtils;
import com.myapp.commcode2api.vo.user.GetUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CacheService cacheService;
    @Override
    @Transactional
    public GetUserVO register(String username,String password,String ip) throws Exception{
        //检查用户名是否已经存在
        User user = userDao.findUserByUsername(username);
        if (user != null){
            cacheService.traceLoginFailByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException("用户已存在");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        String secret = ServiceUtils.getNewSecret();
        newUser.setSecret(secret);
        newUser.setBalance(new BigDecimal(DataOperateConstants.DEFUlTE_BALANCE));
        newUser.setValid(DataOperateConstants.VALID_YES);
        Date now = new Date();
        newUser.setCreateTime(now);
        newUser.setUpdateTime(now);
        newUser.setRecentLoginTime(now);
        newUser.setType(DataOperateConstants.USER_TYPE_COMMON);
        newUser.setLoginIp(ip);
        userDao.add(newUser);
        //查找新用户信息
        user = userDao.findUserById(newUser.getId());
        GetUserVO getUserVO = new GetUserVO();
        BeanUtils.copyProperties(user,getUserVO);
        cacheService.traceCreateByIp(ip, DataOperateConstants.CACHE_SET);
        return getUserVO;
    }

    @Override
    @Transactional
    public GetUserVO login(String username, String password,String ip) throws Exception {
        //检查用户名是否存在
        User user = userDao.findUserByUsername(username);
        if (user == null){
            cacheService.traceLoginFailByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException("用户名不存在");
        }
        //检查密码
        if (!user.getPassword().equals(password)){
            cacheService.traceLoginFailByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException("密码不正确");
        }
        //检查用户其他项
        String checkRes = ServiceUtils.checkCommUserStatus(user);
        if (!StringUtils.isEmpty(checkRes)){
            cacheService.traceLoginFailByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException(checkRes);
        }
        //更新登录地址
        userDao.updateLoginIp(ip,user.getId());
        GetUserVO getUserVO = new GetUserVO();
        BeanUtils.copyProperties(user,getUserVO);
        return getUserVO;
    }

    @Override
    public String resetPassword(String newPassword, String secret) throws Exception {
        return null;
    }

    @Override
    public GetUserVO resetSecret(String secret){
        //更新秘钥
        String newSecret = ServiceUtils.getNewSecret();
        userDao.updateSecret(newSecret,secret);
        //重新获取用户信息
        GetUserVO getUserVO = getUserInfo(newSecret);
        return getUserVO;
    }
    @Override
    public GetUserVO getUserInfo(String secret) {
        User user = userDao.findUserBySecret(secret);
        GetUserVO getUserVO = new GetUserVO();
        BeanUtils.copyProperties(user, getUserVO);
        return getUserVO;
    }
    @Override
    public void checkEntryUser(String username,String password,String openId,String verifyCode,String ip) throws Exception{
        String checkRes = ServiceUtils.userBaseCheck(username,password);
        if (!StringUtils.isEmpty(checkRes)){
            cacheService.traceOtherOpsByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException(checkRes);
        }
        String realCode = cacheService.getCaptchaCodeByOpenId(openId);
        if (!verifyCode.equalsIgnoreCase(realCode)){
            cacheService.traceOtherOpsByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException("验证码不正确");
        }
    }
}
