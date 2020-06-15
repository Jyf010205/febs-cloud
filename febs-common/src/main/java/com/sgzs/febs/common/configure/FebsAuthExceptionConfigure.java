package com.sgzs.febs.common.configure;

import com.sgzs.febs.common.handler.FebsAccessDeniedHandler;
import com.sgzs.febs.common.handler.FebsAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author: jianyufeng
 * @description: 用户访问无权限资源时的异常
 * @date: 2020/6/2 16:17
 */
public class FebsAuthExceptionConfigure {

    /**
     * @ConditionalOnMissingBean(name = "accessDeniedHandler")为例，
     * 当微服务系统的Spring IOC容器中没有名称为accessDeniedHandler的Bean的时候，就将FebsAccessDeniedHandler注册为一个Bean。这样
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public FebsAccessDeniedHandler accessDeniedHandler() {
        return new FebsAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public FebsAuthExceptionEntryPoint authenticationEntryPoint() {
        return new FebsAuthExceptionEntryPoint();
    }
}
