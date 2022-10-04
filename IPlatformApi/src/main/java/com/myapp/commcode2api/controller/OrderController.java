package com.myapp.commcode2api.controller;

import com.alibaba.fastjson.JSONArray;
import com.myapp.commcode2api.component.ResultDTO;
import com.myapp.commcode2api.component.ValidateCreate;
import com.myapp.commcode2api.component.ValidateToken;
import com.myapp.commcode2api.constant.DataOperateConstants;
import com.myapp.commcode2api.constant.ErrorCodeEnums;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.CacheService;
import com.myapp.commcode2api.service.DiscernService;
import com.myapp.commcode2api.service.OrderService;
import com.myapp.commcode2api.util.ServiceUtils;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.discren.DoDiscernVO;
import com.myapp.commcode2api.vo.discren.GetDiscernVO;
import com.myapp.commcode2api.vo.order.GetOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CacheService cacheService;
    @PostMapping("/pay")
    @ValidateCreate
    @ValidateToken
    public ResultDTO pay(@RequestParam(value = "payNum") BigDecimal payNum, HttpServletRequest request) throws Exception{
        String ip = ServiceUtils.getIpAddress(request);
        //判断支付金额合法性
        if (payNum == null || payNum.compareTo(new BigDecimal(10)) < 0){
            cacheService.traceOtherOpsByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException("支付金额不合法");
        }
        //转整
        payNum = new BigDecimal(Integer.parseInt(payNum.toString()));
        String secret = request.getHeader("token");
        GetOrderVO getOrderVO = orderService.doPay(payNum,secret,ip);
        return ServiceUtils.returnResultDTO(getOrderVO,ErrorCodeEnums.SUCCESS);
    }
    @PostMapping("/payList")
    @ValidateToken
    public ResultDTO payList(@RequestParam(value = "pageNum") Integer pageNum,@RequestParam(value = "pageSize") Integer pageSize,HttpServletRequest request) throws Exception{
        String secret = request.getHeader("token");
        PageResult<GetOrderVO> pageResult = orderService.getPayList(pageNum,pageSize,secret);
        return ServiceUtils.returnResultDTO(pageResult,ErrorCodeEnums.SUCCESS);
    }
}
