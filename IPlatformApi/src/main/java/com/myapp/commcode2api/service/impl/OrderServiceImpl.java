package com.myapp.commcode2api.service.impl;

import com.github.pagehelper.PageHelper;
import com.myapp.commcode2api.constant.DataOperateConstants;
import com.myapp.commcode2api.dao.OrderDao;
import com.myapp.commcode2api.dao.UserDao;
import com.myapp.commcode2api.entity.Order;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.CacheService;
import com.myapp.commcode2api.service.OrderService;
import com.myapp.commcode2api.util.ServiceUtils;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.order.GetOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CacheService cacheService;
    @Override
    @Transactional
    public GetOrderVO doPay(BigDecimal payNum, String secret,String ip) throws Exception{
        //获取用户信息
        User user = userDao.findUserBySecret(secret);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setPayNum(payNum);
        Date now = new Date();
        order.setCreateTime(now);
        int i = 0;
        String no = null;
        while (i < 3){
            no = ServiceUtils.getLowercaseAndUppercaseLettersAndNumber(16);
            //判断no是否重复
            Order tmpOrder = orderDao.findOrderByOrderNo(no);
            if (tmpOrder == null){
                break;
            }
            i++;
        }
        //一直重复
        if (i>=3){
            cacheService.traceCreateByIp(ip, DataOperateConstants.CACHE_SET);
            throw new BusinessException("添加支付失败");
        }
        order.setNo(no);
        order.setSecret(secret);
        orderDao.addOrder(order);
        //重查
        Integer id = order.getId();
        order = orderDao.findOrderByOrderId(id);
        GetOrderVO getOrderVO = new GetOrderVO();
        BeanUtils.copyProperties(order,getOrderVO);
        cacheService.traceCreateByIp(ip, DataOperateConstants.CACHE_SET);
        return getOrderVO;
    }
    @Override
    public PageResult<GetOrderVO> getPayList(Integer pageNum, Integer pageSize, String secret){
        PageHelper.startPage(pageNum,pageSize);
        List<GetOrderVO> getOrderVOList = orderDao.findOrdersByUserSecret(secret);
        PageResult<GetOrderVO> pageResult = ServiceUtils.getPageResult(getOrderVOList);
        return pageResult;
    }
}
