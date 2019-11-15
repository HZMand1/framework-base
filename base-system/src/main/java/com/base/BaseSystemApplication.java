package com.base;

import com.base.mymapper.MyMapper;
import com.base.datasource.mybatis.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement
@MapperScan(basePackages = {"com.base.*.dao"}, markerInterface = MyMapper.class)
@Import(DynamicDataSourceRegister.class)
//@ComponentScan(excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {DataSourceAutoConfiguration.class})
//})
public class BaseSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseSystemApplication.class, args);
    }

}
