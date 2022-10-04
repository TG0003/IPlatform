package com.myapp.commcode2api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.commcode2api.entity.User;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.discren.DoDiscernVO;
import com.myapp.commcode2api.vo.discren.GetDiscernVO;

public interface DiscernService {
    //获取识别列表
    PageResult<GetDiscernVO> getDiscernList(Integer pageNum, Integer pageSize, String secret);
    //用户验证码识别接口
    JSONArray doDiscern(DoDiscernVO doDiscernVO, String ip) throws Exception;
    //添加识别
    void addDiscern(DoDiscernVO doDiscernVO, User user, JSONObject discernResInfo, String ip) throws Exception;
}
