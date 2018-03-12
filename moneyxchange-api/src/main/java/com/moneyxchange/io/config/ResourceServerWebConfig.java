package com.moneyxchange.io.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.moneyxchange.io.controller" })
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
    //
}
