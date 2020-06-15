package com.sgzs.febs.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: jianyufeng
 * @description:
 * @PropertySource(value = {"classpath:febs-auth.properties"})用于指定读取的配置文件路径；
 * @ConfigurationProperties(prefix = "febs.auth")指定了要读取的属性的统一前缀名称为febs.auth；
 * @SpringBootConfiguration实质上为@Component的派生注解，用于将FebsAuthProperties纳入到IOC容器中。
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
    //验证码配置
    private FebsValidateCodeProperties validateCode = new FebsValidateCodeProperties();
}
