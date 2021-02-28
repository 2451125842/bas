package com.nju.graduation.project.bas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nju.graduation.project.bas.mapper")
public class BasApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasApplication.class, args);
    }

}
