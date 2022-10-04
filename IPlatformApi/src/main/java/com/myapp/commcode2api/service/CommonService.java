package com.myapp.commcode2api.service;

import com.myapp.commcode2api.exception.BusinessException;

public interface CommonService {
    double getCaptchaPriceByType(String type) throws BusinessException;
}
