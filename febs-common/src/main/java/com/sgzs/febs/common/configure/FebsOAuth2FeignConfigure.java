package com.sgzs.febs.common.configure;

import com.sgzs.febs.common.entity.FebsConstant;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Base64Utils;

/**
 * @author: jianyufeng
 * @description: Feign上手动往请求头上加入令牌即可。
 * @date: 2020/6/9 16:59
 */
public class FebsOAuth2FeignConfigure {

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return requestTemplate -> {
            //请求头添加Zuul Token
            String zuulToken = new String(Base64Utils.encode(FebsConstant.ZUUL_TOKEN_VALUE.getBytes()));
            requestTemplate.header(FebsConstant.ZUUL_TOKEN_HEADER,zuulToken);

            //Feign上加入令牌信息
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails){
                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION , "bearer " + authorizationToken);
            }
        };
    }
}
