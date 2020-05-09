package com.example.gatewayserver.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//自定义一个pre类型的过滤器
@Component
public class AccessFilter extends ZuulFilter {
    private static Logger LOG = LoggerFactory.getLogger(AccessFilter.class);

    //定义过滤器的类型
    @Override
    public String filterType() {
        return "pre";
    }

    //定义过滤器的优先级，数字越小优先级越高
    @Override
    public int filterOrder() {
        return 0;
    }

    //是否执行这个过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器的具体执行方法
    @Override
    public Object run() {
        //获取请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取请求对应的请求
        HttpServletRequest request = ctx.getRequest();
        LOG.info("当前请求的url为 : " + request.getMethod() + " , " + request.getRequestURL().toString());
        String usertoken = request.getParameter("userToken");
        //如果没有usetoken，直接返回
        if (StringUtils.isBlank(usertoken)) {
            LOG.warn("当前请求的url为没有userToken，不能访问");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("请求必须含有userToken");
            return  null;
        }
        return null;
    }
}
