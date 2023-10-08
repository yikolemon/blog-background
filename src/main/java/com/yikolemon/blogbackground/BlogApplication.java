package com.yikolemon.blogbackground;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author  yikolemon
 * @date  2023/10/8
 */
@SpringBootApplication
@MapperScan("com.yikolemon.blogbackground.mapper")
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
