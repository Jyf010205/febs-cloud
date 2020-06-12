package com.sgzs.febs.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/5/29 21:42
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:febs-auth.properties"})
@ConfigurationProperties(prefix = "febs.auth")
public class FebsAuthProperties {
    private FebsClientsProperties[] clients = {};

    private int accessTokenValiditySeconds = 60 * 60 * 24;

    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    //免认证路径
    private String anonUrl;
    //验证码配置类
    private FebsValidateCodeProperties validateCode = new FebsValidateCodeProperties();
}
