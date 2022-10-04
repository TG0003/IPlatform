package com.myapp.commcode2api;

import com.myapp.commcode2api.dao.UserDao;
import com.myapp.commcode2api.exception.BusinessException;
import com.myapp.commcode2api.service.ConfigService;
import com.myapp.commcode2api.service.impl.CommonServiceImpl;
import com.myapp.commcode2api.service.impl.ConfigServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
class CommCode2ApiApplicationTests {
    @Autowired
    private CommonServiceImpl commonService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ConfigServiceImpl configService;
    @Test
    void contextLoads() {
    }
    @Test
    void baseTest() throws BusinessException {
        // System.out.println(commonService.getCaptchaPriceByType("WY"));
        // System.out.println(userDao.findUserBySecret("3380a799c4bc4b84a6814e2b17952001"));
//        System.out.println(configService.t(1).get("age") instanceof Integer);
//        userDao.updateSecret("3380a799c4bc4b84a6814e2b17952001","3380a799c4bc4b84a6814e2b17952a01");
    }
    @Test
    void baseTest2() throws BusinessException {
        // Duration d = Duration.ofSeconds(3);
        // System.out.println(d);
        // Duration dm = Duration.ofMinutes(3);
        // System.out.println(dm);
    }
}
