package com.example.feignclientprovide.service.feignservice;


import com.example.pojo.User;

//结构服务
public interface FeignProvideService {

    public String hello(String name);


    public User hello(String name, Integer age);

    public String hello(User user);

    public String hello();
}
