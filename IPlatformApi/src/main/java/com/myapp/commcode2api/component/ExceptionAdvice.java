package com.myapp.commcode2api.component;

import com.myapp.commcode2api.constant.ErrorCodeEnums;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.util.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wanghuayu
 * @create 2022-03-06 23:39
 */
@RestControllerAdvice
public class ExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @ExceptionHandler(Exception.class)
    public ResultDTO handleException(HttpServletRequest request,Exception e){
        String ip = ServiceUtils.getIpAddress(request);
        // e.printStackTrace();
        logger.info(ip+"==>"+e.getMessage());
        //获取用户异常信息
        String msg;
        if (e instanceof BusinessException){
            msg = e.getMessage();
        }
        else if(e instanceof ServletException){
            msg = e.getMessage();
        }
        else if(e instanceof BindException){
            msg = ((BindException) e).getBindingResult().getFieldError().getDefaultMessage();
        }else {
            msg = "服务异常，请联系管理员";
        }
        return ServiceUtils.returnResultDTO(null,ErrorCodeEnums.FAIL,msg);
    }
}
