package com.sgzs.febs.server.test.service;

import com.sgzs.febs.common.entity.FebsServerConstant;
import com.sgzs.febs.server.test.service.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/9 16:40
 */
@FeignClient(value = FebsServerConstant.FEBS_SERVER_SYSTEM, contextId = "helloServiceClient", fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {

    @GetMapping("hello")
    String hello(@RequestParam("name") String name);
}
