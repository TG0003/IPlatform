package com.myapp.commcode2api.component;

import com.myapp.commcode2api.constant.DataOperateConstants;
import com.myapp.commcode2api.service.CacheService;
import com.myapp.commcode2api.util.ServiceUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class ValidateOtherOpsAOP {
    @Autowired
    private CacheService cacheService;
    @Pointcut("@annotation(com.myapp.commcode2api.component.ValidateOtherOps)")
    public void pointCut(){}
    @Around("pointCut()")
    public Object handle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        //获取请求对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //处理
        String ip = ServiceUtils.getIpAddress(request);
        cacheService.traceOtherOpsByIp(ip, DataOperateConstants.CACHE_CHECK);
        return proceedingJoinPoint.proceed();
    }
}
