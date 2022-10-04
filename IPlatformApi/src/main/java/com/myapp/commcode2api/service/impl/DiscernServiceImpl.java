package com.myapp.commcode2api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.myapp.commcode2api.constant.CacheEnums;
import com.myapp.commcode2api.constant.CaptchaTypeEnums;
import com.myapp.commcode2api.constant.DataOperateConstants;
import com.myapp.commcode2api.constant.ServerConfigConstants;
import com.myapp.commcode2api.dao.DiscernDao;
import com.myapp.commcode2api.dao.UserDao;
import com.myapp.commcode2api.entity.Discern;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.CacheService;
import com.myapp.commcode2api.service.CommonService;
import com.myapp.commcode2api.service.DiscernService;
import com.myapp.commcode2api.service.tool.DoDiscernThread;
import com.myapp.commcode2api.util.ServiceUtils;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.discren.DoDiscernVO;
import com.myapp.commcode2api.vo.discren.GetDiscernVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

@Service
public class DiscernServiceImpl implements DiscernService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DiscernDao discernDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CacheService cacheService;
    @Qualifier(value = "captchaConfRedisManager")
    @Autowired
    private RedisCacheManager captchaConfRedisManager;
    private Semaphore semaphore = new Semaphore(ServerConfigConstants.MAX_DISCERN_CONCURRENT);
    @Override
    public PageResult<GetDiscernVO> getDiscernList(Integer pageNum, Integer pageSize, String secret){
        PageHelper.startPage(pageNum,pageSize);
        List<GetDiscernVO> getDiscernVOList = discernDao.findDiscernsByUserSecret(secret);
        PageResult<GetDiscernVO> pageResult = ServiceUtils.getPageResult(getDiscernVOList);
        return pageResult;
    }
    @Override
    @Transactional
    public void addDiscern(DoDiscernVO doDiscernVO, User user, JSONObject discernResInfo, String ip) throws Exception{
        Discern discern = new Discern();
        int i = 0;
        String no = null;
        while (i < 3){
            no = ServiceUtils.getLowercaseAndUppercaseLettersAndNumber(16);
            //判断no是否重复
            Discern tmpDiscern = discernDao.findDiscernByDiscernNo(no);
            if (tmpDiscern == null){
                break;
            }
            i++;
        }
        //一直重复
        if (i>=3){
            throw new BusinessException("添加识别失败");
        }
        discern.setNo(no);
        discern.setUserId(user.getId());
        discern.setType(doDiscernVO.getType());
        if(CaptchaTypeEnums.WYYD.getCode().equals(doDiscernVO.getType())){
            discern.setCaptchaId(doDiscernVO.getId());
        }else if(CaptchaTypeEnums.GEETEST.getCode().equals(doDiscernVO.getType())){
            discern.setCaptchaId(doDiscernVO.getGt());
        }else if(CaptchaTypeEnums.TX.getCode().equals(doDiscernVO.getType()) || CaptchaTypeEnums.TX_MINI_APP.getCode().equals(doDiscernVO.getType())){
            discern.setCaptchaId(doDiscernVO.getAid());
        }
        discern.setCaptchaReferer(doDiscernVO.getReferer());
        Date now = new Date();
        discern.setDiscernTime(now);
        discern.setCaptchaInfo(discernResInfo.getJSONObject("captchaInfo").toJSONString());
        discern.setResult(discernResInfo.getJSONObject("data").toJSONString());
        String validate = discernResInfo.getJSONObject("data").getString("validate");
        if (!StringUtils.isEmpty(validate)){
            discern.setResultState("成功");
        }else {
            discern.setResultState("失败");
        }
        discern.setTjId(discernResInfo.getJSONObject("data").getString("tjId"));
        discern.setDiscernIp(ip);
        discern.setSecret(doDiscernVO.getSecret());
        discernDao.addDiscern(discern);
    }
    @Transactional
    User beforeDoDiscern(DoDiscernVO doDiscernVO, String ip) throws Exception{
        //初始判断
        ServiceUtils.checkDiscernObject(doDiscernVO);
        User user;
        double price;
        double subtractNUm;
        synchronized(doDiscernVO.getSecret()){
            user = userDao.findUserBySecret(doDiscernVO.getSecret());
            if (user == null){
                cacheService.traceOtherOpsByIp(ip, DataOperateConstants.CACHE_SET);
                throw new BusinessException("无效秘钥，拒绝操作");
            }
            String checkRes = ServiceUtils.checkCommUserStatus(user);
            if (!StringUtils.isEmpty(checkRes)){
                throw new BusinessException(checkRes);
            }
            // 获取类型价格
            price = commonService.getCaptchaPriceByType(doDiscernVO.getType());
            // 检查余额
            if (BigDecimal.valueOf(price).compareTo(user.getBalance())>0){
                throw new BusinessException("余额不足");
            }
            // 可识别次数
            /// 因为count调为1了，所以无需处理这部分逻辑
            // BigDecimal count = user.getBalance().divide(BigDecimal.valueOf(price),0,BigDecimal.ROUND_UP);
            // if (count.compareTo(new BigDecimal(doDiscernVO.getCount()))<0){
            //     doDiscernVO.setCount(count.intValue());
            // }
            // 扣除
            subtractNUm = doDiscernVO.getCount()*price;
            userDao.updateBalanceBySecret(BigDecimal.valueOf(subtractNUm),user.getSecret());
            doDiscernVO.setSubtractNum(subtractNUm);
        }
        return user;
    }
    @Override
    public JSONArray doDiscern(DoDiscernVO doDiscernVO, String ip) throws Exception{
        User user = beforeDoDiscern(doDiscernVO,ip);
        //识别
        DoDiscernThread discernThread = new DoDiscernThread(doDiscernVO,captchaConfRedisManager,semaphore);
        discernThread.start();
        discernThread.join();
        String res = discernThread.getRes();
        return afterDoDiscern(doDiscernVO,user,ip,res);
    }
    @Transactional
    JSONArray afterDoDiscern(DoDiscernVO doDiscernVO, User user,String ip,String res) throws Exception{
        JSONArray retJsonArr = new JSONArray();
        if (!StringUtils.isEmpty(res)){
            JSONObject discernResult = JSONObject.parseObject(res);
            if (discernResult.getInteger("code") == 1){
                //回退金额
                backUserBalance(doDiscernVO.getSubtractNum(),user.getSecret());
                cacheService.traceDiscernExceptionBySecret(doDiscernVO.getSecret(), DataOperateConstants.CACHE_SET);
                throw new BusinessException(discernResult.getString("msg"));
            }
            // 去除redis失败缓存
            cacheService.clearFail(doDiscernVO.getSecret(), CacheEnums.ZERO_CLEAR.getCode(),CacheEnums.DIS_REDIS_MANAGER.getCode());
            //统计识别情况
            JSONArray discernResArr = discernResult.getJSONArray("data");
            for(int i = 0;i < discernResArr.size();i++){
                JSONObject discernResInfo = discernResArr.getJSONObject(i);
                addDiscern(doDiscernVO,user,discernResInfo,ip);
                String validate = discernResInfo.getJSONObject("data").getString("validate");
                if (StringUtils.isEmpty(validate)){
                    //识别失败回退
                    // 因为count为1了，所以subtractNum=price
                    // userDao.updateBalanceBySecret(BigDecimal.valueOf(-price),user.getSecret());
                    backUserBalance(doDiscernVO.getSubtractNum(),user.getSecret());
                }
                retJsonArr.add(discernResInfo.getJSONObject("data"));
            }
        }else {
            //回退金额
            backUserBalance(doDiscernVO.getSubtractNum(),user.getSecret());
            cacheService.traceDiscernExceptionBySecret(doDiscernVO.getSecret(), DataOperateConstants.CACHE_SET);
            throw new BusinessException("请求识别服务异常");
        }
        return retJsonArr;
    }
    @Transactional
    void backUserBalance(double num,String secret){
        userDao.updateBalanceBySecret(BigDecimal.valueOf(-num),secret);
    }
}
