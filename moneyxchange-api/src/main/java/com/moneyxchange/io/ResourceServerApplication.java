package com.moneyxchange.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = { "com.moneyxchange.io" })
@EnableCaching
@EnableScheduling
public class ResourceServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

}