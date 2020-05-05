package com.example.feignclientconsum.controller;

import com.example.feignclientconsum.service.FeignConsumOriginalService;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

//客户端的调用方法
@RestController
public class FeignConsumOriginalController {

    @Autowired
    @Qualifier("feignConsumOriginalService")
    private FeignConsumOriginalService feignConsumOriginalService;

    @RequestMapping(value = "/feign-orignial-feign")
    public String feignConsumer() {
        return feignConsumOriginalService.hello();
    }

    @RequestMapping(value = "/feign-orignial-feign1")
    public String feignConsumer(@RequestParam String name) {
        return feignConsumOriginalService.hello(name);
    }

    @RequestMapping(value = "/feign-orignial-feign2")
    public String feignConsumer(@RequestHeader String name, @RequestHeader Integer age) {
        return feignConsumOriginalService.hello(name, age);
    }

    @RequestMapping(value = "/feign-orignial-feign3")
    public String feignConsumer(@RequestBody User user) {
        return feignConsumOriginalService.hello(user);
    }

}
