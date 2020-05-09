package com.example.gatewayserver.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ThrowExceptionFilter extends ZuulFilter {

    private static Logger LOG = LoggerFactory.getLogger(ThrowExceptionFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        //获取当前上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //一般来说这种处理错误的方式是可以的，但是为了预防有些人没有catch异常
        //后面有加上一个全局的pre,error的错误判断，这里就可以不用try{}catch
        /*
        try {
            LOG.warn("开始抛出错误,ThrowExceptionFilter！！！！");
            doSomeError();
        } catch (Exception e) {
            LOG.error("错误", e);
            ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ctx.set("error.exception", e);
        }*/
        //测试全局捕获pre和route异常
        //doSomeError();
        return null;
    }

    private void doSomeError() {
        throw new RuntimeException("抛出了一个错误");
    }
}
