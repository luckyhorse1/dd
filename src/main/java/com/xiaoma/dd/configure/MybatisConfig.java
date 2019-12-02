package com.xiaoma.dd.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.xiaoma.dd.mapper", "com.xiaoma.dd.dao"})
public class MybatisConfig {
}
