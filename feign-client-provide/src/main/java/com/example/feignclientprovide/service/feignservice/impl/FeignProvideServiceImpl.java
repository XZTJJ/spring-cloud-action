package com.example.feignclientprovide.service.feignservice.impl;

import com.example.feignclientprovide.service.feignservice.FeignProvideService;
import com.example.pojo.User;
import org.springframework.stereotype.Service;

//服务提供方
@Service
public class FeignProvideServiceImpl implements FeignProvideService {

    public String hello(String name) {
        return "hello " + name;
    }


    public User hello(String name, Integer age) {
        return new User(name, age);
    }

    public String hello( User user) {
        return "hello " + user.getName() + ", you age is " + user.getAge();
    }

    public String hello() {
        return "hello dear";
    }
}
