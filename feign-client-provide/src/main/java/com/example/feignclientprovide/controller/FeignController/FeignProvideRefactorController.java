package com.example.feignclientprovide.controller.FeignController;

import com.example.feignclientprovide.service.feignservice.FeignProvideService;
import com.example.pojo.User;
import com.example.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//服务的提供方,重写继承的接口
@RestController
public class FeignProvideRefactorController implements FeignService {

    @Autowired
    private FeignProvideService feignProvideService;

    @Override
    public String hello(@RequestParam("name") String name) {
        return feignProvideService.hello(name);
    }

    @Override
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        return feignProvideService.hello(name, age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return feignProvideService.hello(user);
    }

    @Override
    public String hello() {
        return feignProvideService.hello();
    }
}
