package com.zhouhc.hystrixclientconsum.consum.service;

import com.zhouhc.hystrixclientconsum.consum.pojo.User;

import java.util.List;

public interface UserService {

    //单个请求
    public User findUser(String id);
    //批量的请求
    public List<User> findUserAll(List<String> ids);

    //单个请求，注解式
    public User findUserAnnotation(String id);
    //批量的请求，注解式
    public List<User> findUserAllAnnotation(List<String> ids);

    //单个请求注解是缓存
    public User findUserAnnotationCache(String id);
    public User flushCache(String id);
}
