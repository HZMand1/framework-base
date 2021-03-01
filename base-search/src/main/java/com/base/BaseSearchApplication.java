package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2021/3/1 10:43
 * @Version V1.0
 * @Copyright Copyright (c) 2020
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BaseSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseSearchApplication.class, args);
    }
}
