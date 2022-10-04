package com.myapp.commcode2api.service;

import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.order.GetOrderVO;

import java.math.BigDecimal;

public interface OrderService {
    //用户支付
    GetOrderVO doPay(BigDecimal pagNum, String secret,String ip) throws Exception;
    //获取订单列表
    PageResult<GetOrderVO> getPayList(Integer pageNum, Integer pageSize, String secret);
}
