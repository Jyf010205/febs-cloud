package com.sgzs.febs.auth;

import com.sgzs.febs.common.annotation.EnableFebsAuthExceptionHandler;
import com.sgzs.febs.common.annotation.EnableFebsLettuceRedis;
import com.sgzs.febs.common.annotation.EnableFebsServerProtect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @EnableDiscoveryClient 用户开启服务注册与发现
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFebsServerProtect
@EnableFebsAuthExceptionHandler
@EnableFebsLettuceRedis
@MapperScan("com.sgzs.febs.auth.mapper")
public class FebsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FebsAuthApplication.class, args);
    }

}

