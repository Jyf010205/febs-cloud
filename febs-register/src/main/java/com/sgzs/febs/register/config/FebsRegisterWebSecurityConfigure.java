package com.sgzs.febs.register.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: jianyufeng
 * @description: 使用security保护微服务注册中心
 * @date: 2020/5/28 15:39
 */
@EnableWebSecurity
public class FebsRegisterWebSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**")
                .and()
                //Spring Boot Admin是通过spring-boot-starter-actuator提供的/actuator/**监控接口来实现的，
                // 而我们的微服务都是受Spring Cloud Security保护的，所以我们需要将/actuator/**资源纳入到免认证路径中
                .authorizeRequests().antMatchers("/actuator/**").permitAll();
        super.configure(http);
    }
}
