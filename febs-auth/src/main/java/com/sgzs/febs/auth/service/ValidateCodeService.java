package com.sgzs.febs.auth.service;

import com.sgzs.febs.auth.properties.FebsAuthProperties;
import com.sgzs.febs.auth.properties.FebsValidateCodeProperties;
import com.sgzs.febs.common.entity.FebsConstant;
import com.sgzs.febs.common.exception.ValidateCodeException;
import com.sgzs.febs.common.utils.RedisUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/10 14:17
 */
@Service
public class ValidateCodeService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FebsAuthProperties properties;
    /**
     * 生成验证码
     * @param request
     * @param response
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws ValidateCodeException, IOException {
        String key = request.getParameter("key");
        if (StringUtils.isBlank(key)){
            throw new ValidateCodeException("验证码key不能为空");
        }
        FebsValidateCodeProperties code = properties.getValidateCode();
        setHeader(response,code.getType());

        Captcha captcha = createCaptcha(code);
        redisUtil.set(FebsConstant.CODE_PREFIX + key,StringUtils.lowerCase(captcha.text()),code.getTime());
        captcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码
     * @param key
     * @param value
     */
    public void check(String key,String value) throws ValidateCodeException {
        Object codeInRedis = redisUtil.get(FebsConstant.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new ValidateCodeException("验证码不正确");
        }
    }

    private Captcha createCaptcha(FebsValidateCodeProperties code) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(code.getType(), FebsConstant.GIF)) {
            captcha = new GifCaptcha(code.getWidth(),code.getHeight(),code.getLength());
        }else {
            captcha = new SpecCaptcha(code.getWidth(),code.getHeight(),code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, FebsConstant.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }
}
