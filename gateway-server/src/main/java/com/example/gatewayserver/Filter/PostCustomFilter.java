package com.example.gatewayserver.Filter;

import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

@Component
public class PostCustomFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        doSomeError();
        return null;
    }

    private void doSomeError() {
        throw new RuntimeException("抛出了一个错误");
    }
}
