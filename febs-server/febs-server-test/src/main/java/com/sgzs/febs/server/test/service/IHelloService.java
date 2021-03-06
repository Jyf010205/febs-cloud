package com.sgzs.febs.server.test.service;

import com.sgzs.febs.common.entity.FebsServerConstant;
import com.sgzs.febs.server.test.service.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: jianyufeng
 * @description:
 * 1.value指定远程服务的名称，这个名称对应febs-server-system模块配置文件application.yml里spring.application.name的配置，即注册到Eureka里的服务名称；
 * 2.contextId指定这个Feign Client的别名，当我们定义了多个Feign Client并且value值相同（即调用同一个服务）的时候，需要手动通过contextId设置别名，否则程序将抛出异常；
 * 3.fallbackFactory指定了回退方法，当我们调用远程服务出现异常时，就会调用这个回退方法。fallback也可以指定回退方法，但fallbackFactory指定的回退方法里可以通过Throwable对象打印出异常日志，方便分析问题。
 * @date: 2020/6/9 16:40
 */
@FeignClient(value = FebsServerConstant.FEBS_SERVER_SYSTEM, contextId = "helloServiceClient", fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {

    //feign调用别人的url
    @GetMapping("hello")
    String hello(@RequestParam("name") String name);
}
