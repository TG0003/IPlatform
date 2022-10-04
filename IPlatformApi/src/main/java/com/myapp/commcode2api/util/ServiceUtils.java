package com.myapp.commcode2api.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myapp.commcode2api.component.ResultDTO;
import com.myapp.commcode2api.constant.*;
import com.myapp.commcode2api.dao.ConfigDao;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.discren.DoDiscernVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public class ServiceUtils {
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("remote_addr");
        return ip;
        // return "111.111.111.111";
    }
    public static String userBaseCheck(String username,String password) throws Exception{
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return "用户名或密码不能为空";
        }
        if(!(5 <= username.length() && username.length() <= 10) || !(5 <= password.length() && password.length() <=10)){
            return "用户名或密码长度不合法";
        }
        return "";
    }
    public static <T> ResultDTO returnResultDTO(T data, ErrorCodeEnums state){
        return new ResultDTO().withCode(state.getCode()).withData(data).withMessage(state.getMsg()).withSuccess(state.isSuccess());
    }
    public static <T> ResultDTO returnResultDTO(T data, ErrorCodeEnums state,String definiteMsg){
        return new ResultDTO().withCode(state.getCode()).withData(data).withMessage(definiteMsg).withSuccess(state.isSuccess());
    }
    public static String checkCommUserStatus(User user){
        if(StringUtils.equals(user.getValid(), DataOperateConstants.VALID_NO)){
            return ErrorMessageConstants.INVALID_USER_MSG;
        }
        return "";
    }
    public static String getNewSecret(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static String getLowercaseAndUppercaseLettersAndNumber(Integer length) {
        String uid = "";
        for (Integer i = 0; i < length; i++) {
            int index = (int) Math.round(Math.random() * 2);
            String randChar = "";
            switch (index) {
                case 0:
                    //大写字符
                    randChar = String.valueOf((char) Math.round(Math.random() * 25 + 65));
                    break;
                case 1:
                    //小写字符
                    randChar = String.valueOf((char) Math.round(Math.random() * 25 + 97));
                    break;
                default:
                    //数字
                    randChar = String.valueOf(Math.round(Math.random() * 9));
                    break;
            }
            uid = uid.concat(randChar);
        }
        return uid;
    }
    public static <T> PageResult getPageResult(List<T> dataList){
        PageInfo<T> pageInfo = new PageInfo<>(dataList);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setData(pageInfo.getList());
        PageHelper.clearPage();
        return pageResult;
    }
    public static void checkDiscernObject(DoDiscernVO doDiscernVO) throws BusinessException{
       // if (doDiscernVO.getCount() == null){
       //  count只取1
        doDiscernVO.setCount(DiscernConstants.DEFAULT_DISCERN_COUNT);
       // }
        CaptchaTypeEnums[] captchaTypeEnums = CaptchaTypeEnums.values();
        boolean valid = false;
        for(int i=0;i<captchaTypeEnums.length;i++){
            if (captchaTypeEnums[i].getCode().equals(doDiscernVO.getType())){
                valid = true;
                break;
            }
        }
        if (!valid){
            throw new BusinessException("类型不正确");
        }
        if (CaptchaTypeEnums.WYYD.getCode().equals(doDiscernVO.getType())){
            if (StringUtils.isBlank(doDiscernVO.getId())){
                throw new BusinessException("id不能为空");
            }
            if (StringUtils.isBlank(doDiscernVO.getReferer())){
                throw new BusinessException("referer不能为空");
            }
        }else if(CaptchaTypeEnums.GEETEST.getCode().equals(doDiscernVO.getType())){
            if (StringUtils.isBlank(doDiscernVO.getReferer())){
                throw new BusinessException("referer不能为空");
            }
            if (StringUtils.isBlank(doDiscernVO.getGt())){
                throw new BusinessException("challenge不能为空");
            }
            if (StringUtils.isBlank(doDiscernVO.getChallenge())){
                throw new BusinessException("gt不能为空");
            }
            if (doDiscernVO.getOfType() != null){
                if (!DiscernConstants.OF_TYPE_SLIDE.equals(doDiscernVO.getOfType()) && !DiscernConstants.OF_TYPE_CLICK.equals(doDiscernVO.getOfType())){
                    throw new BusinessException("ofType只能为slide或click");
                }
            }
        }else if(CaptchaTypeEnums.TX.getCode().equals(doDiscernVO.getType()) || CaptchaTypeEnums.TX_MINI_APP.getCode().equals(doDiscernVO.getType())){
            if (StringUtils.isBlank(doDiscernVO.getAid())){
                throw new BusinessException("aid不能为空");
            }
        }
    }
}
