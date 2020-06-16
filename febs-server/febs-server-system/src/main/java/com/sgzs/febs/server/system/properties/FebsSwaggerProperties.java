package com.sgzs.febs.server.system.properties;

import lombok.Data;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/15 21:21
 */
@Data
public class FebsSwaggerProperties {
    private String basePackage;
    private String title;
    private String description;
    private String version;
    private String author;
    private String url;
    private String email;
    private String license;
    private String licenseUrl;

    private String grantUrl;
    private String name;
    private String scope;
}
