package com.sgzs.febs.server.system.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.sgzs.febs.server.system.properties.FebsServerSystemProperties;
import com.sgzs.febs.server.system.properties.FebsSwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: jianyufeng
 * @description: 分页插件、swagger配置
 *
 * securitySchemes：用于配置安全策略，比如配置认证模型，scope等内容；
 * securityContexts：用于配置安全上下文，只有配置了安全上下文的接口才能使用令牌获取资源。
 * @date: 2020/6/12 17:45
 */
@Configuration
@EnableSwagger2
public class FebsWebConfigure {
    @Autowired
    private FebsServerSystemProperties properties;

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    @Bean
    public Docket swaggerApi(){
        FebsSwaggerProperties swagger = properties.getSwagger();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                //swagger配置
                .apiInfo(apiInfo(swagger))
                .securitySchemes(Collections.singletonList(securityScheme(swagger)))
                .securityContexts(Collections.singletonList(securityContext(swagger)));
    }

    private ApiInfo apiInfo(FebsSwaggerProperties swagger) {
        return new ApiInfo(
                swagger.getTitle(),
                swagger.getDescription(),
                swagger.getVersion(),
                null,
                new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail()),
                swagger.getLicense(), swagger.getLicenseUrl(), Collections.emptyList());
    }

    private SecurityScheme securityScheme(FebsSwaggerProperties swagger) {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(swagger.getGrantUrl());

        return new OAuthBuilder()
                .name("febs_oauth_swagger")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes(swagger)))
                .build();
    }

    private SecurityContext securityContext(FebsSwaggerProperties swagger) {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference(swagger.getName(), scopes(swagger))))
                .forPaths(PathSelectors.any())
                .build();
    }

    private AuthorizationScope[] scopes(FebsSwaggerProperties swagger) {
        return new AuthorizationScope[]{
                new AuthorizationScope(swagger.getScope(), "")
        };
    }
}
