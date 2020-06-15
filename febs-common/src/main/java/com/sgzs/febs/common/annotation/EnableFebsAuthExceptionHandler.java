package com.sgzs.febs.common.annotation;

import com.sgzs.febs.common.configure.FebsAuthExceptionConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: jianyufeng
 * @description: 我们使用@Import将FebsAuthExceptionConfigure配置类引入了进来。
 * @date: 2020/6/2 16:21
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FebsAuthExceptionConfigure.class)
public @interface EnableFebsAuthExceptionHandler {
}
