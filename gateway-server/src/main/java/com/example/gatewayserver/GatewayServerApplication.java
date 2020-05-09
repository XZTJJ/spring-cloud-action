package com.example.gatewayserver;

import com.example.gatewayserver.Filter.CustomFilterProcessor;
import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

//启动网关服务功能
@EnableZuulProxy
@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
        //设置自定义的FilterProcessor，用于处理post的错误
        FilterProcessor.setProcessor(new CustomFilterProcessor());
    }

}
