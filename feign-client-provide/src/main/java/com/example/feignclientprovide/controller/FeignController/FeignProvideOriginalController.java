package com.example.feignclientprovide.controller.FeignController;

import com.example.feignclientprovide.service.feignservice.FeignProvideService;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//服务的提供方,重写继承的接口
@RestController
public class FeignProvideOriginalController {
    @Autowired
    private FeignProvideService feignProvideService;

    @RequestMapping(value = "/feign4")
    public String hello(@RequestParam("name") String name) {
        return feignProvideService.hello(name);
    }

    @RequestMapping(value = "/feign5")
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        return feignProvideService.hello(name, age);
    }

    @RequestMapping(value = "/feign6")
    public String hello(@RequestBody User user) {
        return feignProvideService.hello(user);
    }

    @RequestMapping(value = "/feign")
    public String hello() {
        return feignProvideService.hello();
    }

}
