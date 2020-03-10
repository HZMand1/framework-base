package com.base;

import com.base.mymapper.MyMapper;
import com.base.datasource.mybatis.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
@Import(DynamicDataSourceRegister.class)
@ServletComponentScan
@ComponentScan({"com.base.schedule","com.base"})
@EnableTransactionManagement
@MapperScan(basePackages = {"com.base.*.dao"}, markerInterface = MyMapper.class)
public class BaseSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseSystemApplication.class, args);
    }

}
