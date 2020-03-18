package com.zhouhc.hystrixclientconsum.consum.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.zhouhc.hystrixclientconsum.consum.pojo.User;
import com.zhouhc.hystrixclientconsum.consum.service.UserService;

import java.util.ArrayList;
import java.util.List;

//用于批量请求的command命令
public class UserBatchCommand extends HystrixCommand<List<User>> {

    //实际调用的的执行的过程
    private UserService userService;
    //id
    private List<String> ids;

    public UserBatchCommand(UserService userService, List<String> ids) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserBatchCommand"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("UserBatchCommand")));
        this.userService = userService;
        this.ids = ids;
    }

    //实际执行的方式
    @Override
    protected List<User> run() throws Exception {
        return userService.findUserAll(ids);
    }

    @Override
    protected List<User> getFallback() {
        List<User> tempUser = new ArrayList<User>();
        tempUser.add(new User("UserBatchCommand", "UserBatchCommand", "UserBatchCommand"));
        tempUser.add(new User("UserBatchCommand", "UserBatchCommand", "UserBatchCommand"));
        tempUser.add(new User("UserBatchCommand", "UserBatchCommand", "UserBatchCommand"));
        return tempUser;
    }

}
