package com.zhouhc.hystrixclientprovide.provide.service;

import com.zhouhc.hystrixclientprovide.provide.pojo.User;

import java.util.List;

public interface HystrixProvideService {
    public String conntection(String id);

    public User findUserById(String id);

    public List<User> findUserAll(String ids);


}
