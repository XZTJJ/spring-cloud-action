package com.zhouhc.hystrixclientconsum.consum.service.imp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.zhouhc.hystrixclientconsum.consum.pojo.User;
import com.zhouhc.hystrixclientconsum.consum.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
//用于处理批量或者单个的请求
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User findUser(String id) {
        return  restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);

    }

    @Override
    public List<User> findUserAll(List<String> ids) {
        System.out.println("finaAll request:---------" + ids + "Thread.currentThread().getName():-------" + Thread.currentThread().getName());
        //返回回来的内容一定要使用数组的形式，不让会报错，提示转坏失败错误A
        User[] usersArray = restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUserAll?ids={1}", User[].class, StringUtils.join(ids,","));
        return Arrays.asList(usersArray);

    }

    //单个请求，注解式
    @HystrixCollapser(batchMethod = "findUserAllAnnotation",collapserProperties = {
            @HystrixProperty(name="timerDelayInMilliseconds",value = "200")
    })
    @Override
    public User findUserAnnotation(String id) {
        return restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);
    }

    //批量请求，注解式
    @HystrixCommand(fallbackMethod = "findUserAllAnnotationBack")
    @Override
    public List<User> findUserAllAnnotation(List<String> ids) {
        System.out.println("finaAll request:---------" + ids + "Thread.currentThread().getName():-------" + Thread.currentThread().getName());
        //返回回来的内容一定要使用数组的形式，不让会报错，提示转坏失败错误A
        User[] usersArray = restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUserAll?ids={1}", User[].class, StringUtils.join(ids,","));
        return Arrays.asList(usersArray);
    }

    //服务降级
    public List<User> findUserAllAnnotationBack(List<String> ids){
        List<User> tempUser = new ArrayList<User>();
        tempUser.add(new User("UserBatchCommand", "UserBatchCommand", "UserBatchCommand"));
        tempUser.add(new User("UserBatchCommand", "UserBatchCommand", "UserBatchCommand"));
        tempUser.add(new User("UserBatchCommand", "UserBatchCommand", "UserBatchCommand"));
        return tempUser;
    }

    //注解式缓存
    @Override
    @CacheResult(cacheKeyMethod = "getUserCacheKey")
    @HystrixCommand()
    public User findUserAnnotationCache(String id) {
        return restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);
    }

    //注解清楚缓存
    @CacheRemove(commandKey = "findUserAnnotationCache",cacheKeyMethod = "getUserCacheKey")
    @HystrixCommand
    @Override
    public User flushCache(String id) {
        System.out.println("清楚緩存成功");
        return null;
    }

    private String getUserCacheKey(String id){
        return String.valueOf(id);
    }
}
