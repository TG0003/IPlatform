package com.myapp.commcode2api.component;

import com.myapp.commcode2api.constant.DataOperateConstants;
import com.myapp.commcode2api.constant.ErrorMessageConstants;
import com.myapp.commcode2api.dao.UserDao;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.util.ServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class ValidateTokenAOP {
    @Autowired
    private UserDao userDao;
    @Pointcut("@annotation(com.myapp.commcode2api.component.ValidateToken)")
    public void loginPointCut(){}
    @Around("loginPointCut()")
    public Object tokenHandle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        //获取请求对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //处理
        //请求头
        String loginToken =  request.getHeader("token");
        //注解处理
        Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
        //方法签名
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = clazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        ValidateToken annotation = method.getAnnotation(ValidateToken.class);
        boolean admin = annotation.admin();
        if(com.myapp.common.StringUtils.isNull(loginToken)){
            throw new BusinessException(ErrorMessageConstants.INVALID_TOKEN_MSG);
        }
        //检查token
        User user = userDao.findUserBySecret(loginToken);
        if (user == null){
            throw new BusinessException(ErrorMessageConstants.INVALID_TOKEN_MSG);
        }
        //进一步检验
        String checkRes = ServiceUtils.checkCommUserStatus(user);
        if (!StringUtils.isEmpty(checkRes)){
            throw new BusinessException(checkRes);
        }
        //需要校验是否十管理员
        if(admin && !StringUtils.equals(user.getType(), DataOperateConstants.USER_TYPE_ADMIN)) {
            throw new BusinessException(ErrorMessageConstants.NO_PERMISSION_MSG);
        }
        return proceedingJoinPoint.proceed();
    }
}
