package com.sgzs.febs.common.validator;

import com.sgzs.febs.common.annotation.IsMobile;
import com.sgzs.febs.common.entity.RegexpConstant;
import com.sgzs.febs.common.utils.FebsUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: jianyufeng
 * @description: 编写具体的校验逻辑
 * @date: 2020/6/13 16:08
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                //正则表达式
                String regex = RegexpConstant.MOBILE_REG;
                return FebsUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
