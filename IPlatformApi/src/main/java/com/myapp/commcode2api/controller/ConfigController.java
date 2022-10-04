package com.myapp.commcode2api.controller;

import com.myapp.commcode2api.component.ResultDTO;
import com.myapp.commcode2api.component.ValidateToken;
import com.myapp.commcode2api.constant.ErrorCodeEnums;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.ConfigService;
import com.myapp.commcode2api.service.OrderService;
import com.myapp.commcode2api.util.ServiceUtils;
import com.myapp.commcode2api.vo.PageResult;
import com.myapp.commcode2api.vo.order.GetOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
public class ConfigController {
    @Autowired
    private ConfigService configService;
    @GetMapping("/getConfig")
    public ResultDTO getConfig(@RequestParam(value = "configName")String configName,HttpServletRequest request) throws Exception{
        String confContent = configService.getConfig(configName);
        return ServiceUtils.returnResultDTO(confContent,ErrorCodeEnums.SUCCESS);
    }
}
