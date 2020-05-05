package com.example.feignclientconsum.service;

import com.example.feignclientconsum.service.impl.FeignConsumOriginalServiceFallback;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//客户端的服务方法调用，直接通过Feign调用服务提供方的方法了
@FeignClient(name="feign-client-provide",fallback = FeignConsumOriginalServiceFallback.class)
//首选项的Bean
//@Primary
//自定义Bean的名字
@Qualifier("feignConsumOriginalService")
public interface FeignConsumOriginalService {
    //原始的调用方法
    //1.不带参数
    @RequestMapping("/feign")
    public String hello();
    //2.带参数
    @RequestMapping("/feign4")
    public String hello(@RequestParam("name") String name);
    //2.带参数
    @RequestMapping("/feign5")
    public String hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);
    //2.带参数
    @RequestMapping("/feign6")
    public String hello(@RequestBody User user);


}
