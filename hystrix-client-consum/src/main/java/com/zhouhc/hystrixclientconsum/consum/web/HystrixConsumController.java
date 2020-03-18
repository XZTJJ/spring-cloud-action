package com.zhouhc.hystrixclientconsum.consum.web;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.zhouhc.hystrixclientconsum.consum.command.UserAsynchronousCommand;
import com.zhouhc.hystrixclientconsum.consum.command.UserObservableCommand;
import com.zhouhc.hystrixclientconsum.consum.command.UserSynchronizeCommand;
import com.zhouhc.hystrixclientconsum.consum.command.UserToObservableCommand;
import com.zhouhc.hystrixclientconsum.consum.pojo.User;
import com.zhouhc.hystrixclientconsum.consum.service.HystrixConsumService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/hystrix")
public class HystrixConsumController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HystrixConsumService hystrixConsumService;

    @RequestMapping("/startConsum")
    public String startConsum(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default";
        return hystrixConsumService.startHystrixConsum(id);
    }

    //使用command的注解同步方式进行开发
    @RequestMapping("/findUserAnnotationSynchronize")
    public User findUserAnnotationSynchronize(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        return hystrixConsumService.findUserAnnotationSynchronize(id);
    }

    //使用command的注解异步方式进行开发
    @RequestMapping("/findUserAnnotationAsynchronous")
    public User findUserAnnotationAsynchronous(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        return hystrixConsumService.findUserAnnotationAsynchronous(id);
    }


    //使用command的同步方式进行开发
    @RequestMapping("/findUserSynchronizeCommand")
    public User findUserSynchronizeCommand(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        //Hystrix上下文进行初始化,不然会报初始化错误
        HystrixRequestContext.initializeContext();
        User user1 = hystrixConsumService.findUserSynchronizeCommand(id);
        //清理缓存
        UserSynchronizeCommand.flushCache(id);
        User user2 = hystrixConsumService.findUserSynchronizeCommand(id);
        User user3 = hystrixConsumService.findUserSynchronizeCommand(id);
        return user3;
    }

    //使用command的异步方式进行开发
    @RequestMapping("/findUserAsynchronousCommand")
    public User findUserAsynchronousCommand(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        //Hystrix上下文进行初始化,不然会报初始化错误
        HystrixRequestContext.initializeContext();
        User user1 = hystrixConsumService.UserAsynchronousCommand(id);
        //清除缓存
        UserAsynchronousCommand.flushCache(id);
        User user2 = hystrixConsumService.UserAsynchronousCommand(id);
        User user3 = hystrixConsumService.UserAsynchronousCommand(id);
        return  user3;
    }

    //使用HystrixObservableCommand，进行多条返回
    @RequestMapping("/findUserObservableCommand")
    public List<User> findUserObservableCommand(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        //初始化HystrixRequest缓存
        HystrixRequestContext.initializeContext();
        List<User> userObservableCommand1 = hystrixConsumService.findUserObservableCommand(id);
        UserObservableCommand.flushCache(StringUtils.replaceChars(id,"-",""));
        List<User> userObservableCommand2 = hystrixConsumService.findUserObservableCommand(id);
        List<User> userObservableCommand3 = hystrixConsumService.findUserObservableCommand(id);
        return userObservableCommand3;
    }

    //使用HystrixObservableCommand，进行多条返回
    @RequestMapping("/findUserToObservableCommand")
    public List<User> findUserToObservableCommand(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        //请求缓存初始化
        HystrixRequestContext.initializeContext();
        List<User> userToObservableCommand1 = hystrixConsumService.findUserToObservableCommand(id);
        //清除缓存
        UserToObservableCommand.flushCache(StringUtils.replaceChars(id,"-",""));
        List<User> userToObservableCommand2= hystrixConsumService.findUserToObservableCommand(id);
        List<User> userToObservableCommand3 = hystrixConsumService.findUserToObservableCommand(id);
        return userToObservableCommand3;
    }

    //使用HystrixObservableCommand注解的方式，进行多条返回
    @RequestMapping("/findUserObservableAnnotationCommand")
    public List<User> findUserObservableAnnotationCommand(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        return hystrixConsumService.findUserObservableAnnotationCommand(id);
    }

    //使用UserCollapseCommand进行请求的合并操作
    @RequestMapping("/findUserCollapseCommand")
    public List<User> findUserCollapseCommand(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        //请求缓存初始化
        HystrixRequestContext.initializeContext();
        return hystrixConsumService.findUserCollapseCommand(id);
    }

    //使用HystrixCollapse进行请求的合并操作，注解的方式
    @RequestMapping("/findUserAnnotationCollapseCommand")
    public List<User> findUserAnnotationCollapseCommand(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        //请求缓存初始化
        HystrixRequestContext.initializeContext();
        return hystrixConsumService.findUserAnnotationCollapseCommand(id);
    }


    //使用注解时3的缓存
    @RequestMapping("/findUserAnnotationCache")
    public List<User> findUserAnnotationCache(@RequestParam(required = false) String id) {
        if (StringUtils.isBlank(id))
            id = "default-consum";
        //请求缓存初始化
        HystrixRequestContext.initializeContext();
        return hystrixConsumService.findUserAnnotationCache(id);
    }
}
