package com.sgzs.febs.auth.config;

import com.sgzs.febs.auth.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: jianyufeng
 * @description: 安全配置类 过滤器
 * FebsSecurityConfigure用于处理/oauth开头的请求，Spring Cloud OAuth内部定义的获取令牌，刷新令牌的请求地址都是以/oauth/开头的，也就是说FebsSecurityConfigure用于处理和令牌相关的请求；
 * 资源ResourceServerConfigurerAdapter order 为 3
 * 如果没有这个类 就被resource拦截住
 * @date: 2020/5/28 16:38
 */
@Order(2)
@EnableWebSecurity
public class FebsSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Autowired
    ValidateCodeFilter validateCodeFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //(requestMatchers())->FebsSecurityConfigure安全配置类只对/oauth/开头的请求有效。
        /**
         * 将ValidateCodeFilter过滤器添加到Spring Security过滤器链中，
         * 并且位于UsernamePasswordAuthenticationFilter过滤器前
         */
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .requestMatchers()
                .antMatchers("/oauth/**")
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
            .and()
                .csrf().disable();
    }
}
