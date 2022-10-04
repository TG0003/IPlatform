package com.myapp.commcode2api.controller;

import com.myapp.commcode2api.component.*;
import com.myapp.commcode2api.constant.DataOperateConstants;
import com.myapp.commcode2api.constant.ErrorCodeEnums;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.CacheService;
import com.myapp.commcode2api.service.UserService;
import com.myapp.commcode2api.util.ServiceUtils;
import com.myapp.commcode2api.vo.user.GetUserVO;
import com.ramostear.captcha.HappyCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CacheService cacheService;
    @ValidateOtherOps
    @ValidateCreate
    @PostMapping("/register")
    public ResultDTO register(@RequestParam(value = "username")String username,
                              @RequestParam(value = "password")String password,
                              @RequestParam(value = "confirmPassword")String confirmPassword,
                              @RequestParam(value = "openId")String openId,
                              @RequestParam(value = "verifyCode")String verifyCode,
                              HttpServletRequest request) throws Exception{
        String ip = ServiceUtils.getIpAddress(request);
        userService.checkEntryUser(username,password,openId,verifyCode,ip);
        //检查确认密码
        if (!StringUtils.equals(password,confirmPassword)){
            cacheService.traceOtherOpsByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException("两次密码输入不一致");
        }
        GetUserVO getUserVO = userService.register(username,password,ip);
        cacheService.rmCaptchaCodeByOpenId(openId);
        return ServiceUtils.returnResultDTO(getUserVO,ErrorCodeEnums.SUCCESS);
    }
    @ValidateOtherOps
    @ValidateLogin
    @PostMapping("/login")
    public ResultDTO login(@RequestParam(value = "username")String username,
                           @RequestParam(value = "password")String password,
                           @RequestParam(value = "openId")String openId,
                           @RequestParam(value = "verifyCode")String verifyCode,
                           HttpServletRequest request) throws Exception{
        String ip = ServiceUtils.getIpAddress(request);
        userService.checkEntryUser(username,password,openId,verifyCode,ip);
        GetUserVO getUserVO = userService.login(username,password,ip);
        cacheService.rmCaptchaCodeByOpenId(openId);
        return ServiceUtils.returnResultDTO(getUserVO,ErrorCodeEnums.SUCCESS);
    }
    @PostMapping("/refreshSecret")
    @ValidateToken
    public ResultDTO refreshSecret(HttpServletRequest request) throws Exception{
        String secret = request.getHeader("token");
        GetUserVO getUserVO = userService.resetSecret(secret);
        return ServiceUtils.returnResultDTO(getUserVO,ErrorCodeEnums.SUCCESS);
    }
    @PostMapping("/userInfo")
    @ValidateToken
    public ResultDTO userInfo(HttpServletRequest request) throws Exception{
        String secret = request.getHeader("token");
        GetUserVO getUserVO = userService.getUserInfo(secret);
        return ServiceUtils.returnResultDTO(getUserVO,ErrorCodeEnums.SUCCESS);
    }
    @GetMapping("/captcha")
    public void newCaptcha(@RequestParam("openId")String openId, HttpServletRequest request, HttpServletResponse response){
        HappyCaptcha.require(request,response).build().finish();
        String code = (String) request.getSession().getAttribute("happy-captcha");
        cacheService.storeCaptchaCodeByOpenId(openId,code);
    }
}
