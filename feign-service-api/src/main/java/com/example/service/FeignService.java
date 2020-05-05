package com.example.service;

import com.example.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//调用结点服务
@RequestMapping("/refactor")
public interface FeignService {

    @RequestMapping(value = "/feign4")
    public String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/feign5")
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/feign6")
    public String hello(@RequestBody User user);

    @RequestMapping(value = "/feign")
    public String hello();
}
