package com.example.feignclientconsum.service.impl;

import com.example.feignclientconsum.service.FeignConsumOriginalService;
import com.example.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//定义服务降级
@Service
public class FeignConsumOriginalServiceFallback  implements FeignConsumOriginalService {

    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello(@RequestParam("name") String name) {
        return "error";
    }

    @Override
    public String hello(@RequestHeader("name") String name,@RequestHeader("age") Integer age) {
        return "error";
    }

    @Override
    public String hello(@RequestBody User user) {
        return "error";
    }
}
