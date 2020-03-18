package com.zhouhc.hystrixclientprovide.provide.web;


import com.zhouhc.hystrixclientprovide.provide.pojo.User;
import com.zhouhc.hystrixclientprovide.provide.service.HystrixProvideService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/hystrix")
public class HystrixProvideController {
    @Autowired
    private HystrixProvideService hystrixProvideService;

    //测试
    @RequestMapping("/connection")
    public String connection(@RequestParam(required = false) String id){
        if(StringUtils.isBlank(id))
            id = "success";
       return hystrixProvideService.conntection(id);
    }

    @RequestMapping("/findUser")
    public User findUserById(@RequestParam(required = false) String id){
        if(StringUtils.isBlank(id))
            id = "0";
        return hystrixProvideService.findUserById(id);
    }

    //处理批量请求的
    @RequestMapping("/findUserAll")
    public List<User> findUserAll(@RequestParam(required = false) String ids){
        if(StringUtils.isBlank(ids))
            ids = "0,1,2";
        return hystrixProvideService.findUserAll(ids);
    }
}
