package com.sgzs.febs.auth.config;

import com.sgzs.febs.auth.filter.ValidateCodeFilter;
import com.sgzs.febs.auth.service.FebsUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: jianyufeng
 * @description: 安全配置类
 * @date: 2020/5/28 16:38
 */
@Order(2)
@EnableWebSecurity
public class FebsSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Autowired
    private FebsUserDetailService UserDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .requestMatchers()
                .antMatchers("/oauth/**")
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
            .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UserDetailService).passwordEncoder(passwordEncoder);
    }
}
