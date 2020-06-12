package com.sgzs.febs.server.test.service.fallback;

import com.sgzs.febs.server.test.service.IHelloService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/9 16:44
 */
@Slf4j
@Component
public class HelloServiceFallback implements FallbackFactory<IHelloService> {
    @Override
    public IHelloService create(Throwable throwable) {
        return name -> {
            log.error("调用FEBS-Server-System服务出错",throwable);
            return "调用出错";
        };
    }
}
