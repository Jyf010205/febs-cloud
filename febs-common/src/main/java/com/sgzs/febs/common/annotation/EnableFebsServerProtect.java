package com.sgzs.febs.common.annotation;

import com.sgzs.febs.common.configure.FebsServerProtectConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author jianyufeng
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FebsServerProtectConfigure.class)
public @interface EnableFebsServerProtect {

}
