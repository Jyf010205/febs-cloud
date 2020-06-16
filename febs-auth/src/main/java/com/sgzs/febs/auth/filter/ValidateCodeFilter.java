package com.sgzs.febs.auth.filter;

import com.sgzs.febs.auth.service.ValidateCodeService;
import com.sgzs.febs.common.entity.FebsResponse;
import com.sgzs.febs.common.exception.ValidateCodeException;
import com.sgzs.febs.common.utils.FebsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: jianyufeng
 * @description: 用于拦截请求并校验验证码的正确性
 * @date: 2020/6/12 16:15
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    @Autowired
    private ValidateCodeService validateCodeService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String clientId = getClientId(header, request);

        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/oauth/token", HttpMethod.POST.toString());
        if (matcher.matches(request)
                && StringUtils.equalsIgnoreCase(request.getParameter("grant_type"),"password")
                && !StringUtils.equalsIgnoreCase(clientId,"swagger")){
            try {
                validateCode(request);
                filterChain.doFilter(request,response);
            } catch (ValidateCodeException e) {
                FebsResponse febsResponse = new FebsResponse();
                FebsUtil.makeResponse(response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, febsResponse.message(e.getMessage()));
                log.error(e.getMessage(), e);
            }
        }else{
            filterChain.doFilter(request,response);
        }

    }

    private void validateCode(HttpServletRequest httpServletRequest) throws ValidateCodeException {
        String code = httpServletRequest.getParameter("code");
        String key = httpServletRequest.getParameter("key");
        validateCodeService.check(key, code);
    }

    //这个方法用于从请求头部获取ClientId信息
    private String getClientId(String header, HttpServletRequest request) {
        String clientId = "";
        try {
            byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
            byte[] decoded;
            decoded = Base64.getDecoder().decode(base64Token);

            String token = new String(decoded, StandardCharsets.UTF_8);
            int delim = token.indexOf(":");
            if (delim != -1) {
                clientId = new String[]{token.substring(0, delim), token.substring(delim + 1)}[0];
            }
        } catch (Exception ignore) {
        }
        return clientId;
    }
}
