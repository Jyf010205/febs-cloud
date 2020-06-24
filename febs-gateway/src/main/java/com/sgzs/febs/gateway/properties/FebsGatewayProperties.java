package com.sgzs.febs.gateway.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: jianyufeng
 * @description: 定义禁止客户端访问资源
 * @date: 2020/6/24 17:03
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:febs-gateway.properties"})
@ConfigurationProperties(prefix = "febs.gateway")
public class FebsGatewayProperties {
    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;
}
