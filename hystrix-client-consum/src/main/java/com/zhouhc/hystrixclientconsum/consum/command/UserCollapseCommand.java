package com.zhouhc.hystrixclientconsum.consum.command;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.zhouhc.hystrixclientconsum.consum.pojo.User;
import com.zhouhc.hystrixclientconsum.consum.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//合并请求的处理类,和单个请求，通过,分割的请求相比，这种请求能自动合并，一段时间时间内的请求
public class UserCollapseCommand extends HystrixCollapser<List<User>,User,String> {

    private UserService userService;
    private String id;

    //构造函数
    public UserCollapseCommand(UserService userService, String id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand"))
        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        this.userService = userService;
        this.id = id;
    }

    //返回每一个参数
    @Override
    public String getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, String>> collapsedRequests) {
        //初始化大小
        List<String> userIds = new ArrayList<String>(collapsedRequests.size());
        //增加所有的参数
        userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        //合并了所有的请求了
        return new UserBatchCommand(userService,userIds);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, String>> collapsedRequests) {
        //用于请求返回的结果
        int count = 0;
        for(CollapsedRequest<User,String> collapsedRequest : collapsedRequests ){
            User user = batchResponse.get(count);
            count++;
            collapsedRequest.setResponse(user);
        }
    }
}
