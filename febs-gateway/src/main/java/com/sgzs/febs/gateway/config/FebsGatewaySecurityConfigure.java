package com.sgzs.febs.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: jianyufeng
 * @description: 因为febs-gateway引入了febs-common模块，febs-common模块包含了Spring Cloud Security依赖，所以我们需要定义一个自己的WebSecurity配置类，来覆盖默认的
 * @date: 2020/5/29 16:48
 */
@EnableWebSecurity
public class FebsGatewaySecurityConfigure extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}
