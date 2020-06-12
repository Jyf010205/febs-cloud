package com.sgzs.febs.common.selector;

import com.sgzs.febs.common.configure.FebsAuthExceptionConfigure;
import com.sgzs.febs.common.configure.FebsOAuth2FeignConfigure;
import com.sgzs.febs.common.configure.FebsServerProtectConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/9 20:59
 */
public class FebsCloudApplicationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                FebsAuthExceptionConfigure.class.getName(),
                FebsOAuth2FeignConfigure.class.getName(),
                FebsServerProtectConfigure.class.getName()
        };
    }
}
