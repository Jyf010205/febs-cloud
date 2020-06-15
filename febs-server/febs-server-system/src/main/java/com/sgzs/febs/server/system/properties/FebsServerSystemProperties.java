package com.sgzs.febs.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/15 21:22
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:febs-server-system.properties"})
@ConfigurationProperties(prefix = "febs.server.system")
public class FebsServerSystemProperties {
    /**
     * 免认证URL
     */
    private String anonUrl;

    private FebsSwaggerProperties swagger = new FebsSwaggerProperties();
}
