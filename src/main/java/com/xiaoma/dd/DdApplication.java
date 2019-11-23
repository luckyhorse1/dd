package com.xiaoma.dd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaoma.dd.mapper")
public class DdApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdApplication.class, args);
    }

}
