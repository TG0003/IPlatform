package com.myapp.commcode2api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CommCode2ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommCode2ApiApplication.class, args);
    }

}
