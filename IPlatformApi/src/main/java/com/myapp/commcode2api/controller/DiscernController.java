package com.myapp.commcode2api.controller;

import com.alibaba.fastjson.JSONArray;
import com.myapp.commcode2api.component.ResultDTO;
import com.myapp.commcode2api.component.ValidateDiscern;
import com.myapp.commcode2api.component.ValidateOtherOps;
import com.myapp.commcode2api.component.ValidateToken;
import com.myapp.commcode2api.constant.ErrorCodeEnums;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.DiscernService;
import com.myapp.commcode2api.util.ServiceUtils;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.discren.DoDiscernVO;
import com.myapp.commcode2api.vo.discren.GetDiscernVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class DiscernController {
    @Autowired
    private DiscernService discernService;
    @PostMapping("/discernList")
    @ValidateToken
    public ResultDTO discernList(@RequestParam(value = "pageNum") Integer pageNum,@RequestParam(value = "pageSize") Integer pageSize,HttpServletRequest request) throws Exception{
        String secret = request.getHeader("token");
        PageResult<GetDiscernVO> pageResult = discernService.getDiscernList(pageNum,pageSize,secret);
        return ServiceUtils.returnResultDTO(pageResult,ErrorCodeEnums.SUCCESS);
    }
    @CrossOrigin
    @ValidateOtherOps
    @ValidateDiscern
    @PostMapping("/discern")
    public ResultDTO discern(@Valid @RequestBody DoDiscernVO doDiscernVO, HttpServletRequest request) throws Exception{
        // String ip = ServiceUtils.getIpAddress(request);
        // JSONArray retJsonArr = discernService.doDiscern(doDiscernVO,ip);
        // return ServiceUtils.returnResultDTO(retJsonArr,ErrorCodeEnums.SUCCESS);
        throw new BusinessException("该识别接口已失效，请及时参照文档进行同步更新");
    }
    @CrossOrigin
    @ValidateOtherOps
    @ValidateDiscern
    @PostMapping("/interpret")
    public ResultDTO interpret(@Valid @RequestBody DoDiscernVO doDiscernVO, HttpServletRequest request) throws Exception{
        String ip = ServiceUtils.getIpAddress(request);
        JSONArray retJsonArr = discernService.doDiscern(doDiscernVO,ip);
        return ServiceUtils.returnResultDTO(retJsonArr,ErrorCodeEnums.SUCCESS);
    }
}
