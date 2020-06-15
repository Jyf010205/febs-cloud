package com.sgzs.febs.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sgzs.febs.common.entity.FebsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jianyufeng
 * @description: 上面的表格可以看到，PreDecorationFilter用于处理请求上下文，
 * 优先级为5，所以我们可以定义一个优先级在PreDecorationFilter之后的过滤器，这样便可以拿到请求上下文。
 *
 * 在网关转发请求前，请求头部加入网关信息，然后在处理请求的微服务模块里定义全局拦截器FebsServerProtectInterceptor，
 * 校验请求头部的网关信息，这样就能避免客户端直接访问微服务了
 * @date: 2020/6/9 17:26
 */
@Slf4j
@Component
public class FebsGatewayRequestFilter extends ZuulFilter {
    /**
     * filterType，对应Zuul生命周期的四个阶段：pre、post、route和error，我们要在请求转发出去前添加请求头，所以这里指定为pre；
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器的优先级，数字越小，优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 6;
    }

    /**
     * 方法返回boolean类型，true时表示是否执行该过滤器的run方法，false则表示不执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
        HttpServletRequest request = ctx.getRequest();
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("请求URI：{}，HTTP Method：{}，请求IP：{}，ServerId：{}", uri, method, host, serviceId);

        byte[] token = Base64Utils.encode(FebsConstant.ZUUL_TOKEN_VALUE.getBytes());
        ctx.addZuulRequestHeader(FebsConstant.ZUUL_TOKEN_HEADER, new String(token));
        return null;
    }
}
