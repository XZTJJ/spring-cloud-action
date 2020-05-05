package com.example.feignclientconsum.controller;

import com.example.feignclientconsum.service.FeignConsumRefactorService;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeignConsumRefactorController {
    //直接调用方法
    @Autowired
    @Qualifier("feignConsumRefactorService")
    private FeignConsumRefactorService feignConsumRefactorService;


    @RequestMapping(value = "/feign-refactor-feign")
    public String feignConsumer() {
        return feignConsumRefactorService.hello();
    }

    @RequestMapping(value = "/feign-refactor-feign1")
    public String feignConsumer(@RequestParam String name) {
        return feignConsumRefactorService.hello(name);
    }

    @RequestMapping(value = "/feign-refactor-feign2")
    public User feignConsumer(@RequestHeader String name, @RequestHeader Integer age) {
        return feignConsumRefactorService.hello(name, age);
    }

    @RequestMapping(value = "/feign-refactor-feign3")
    public String feignConsumer(@RequestBody User user) {
        return feignConsumRefactorService.hello(user);
    }
}
