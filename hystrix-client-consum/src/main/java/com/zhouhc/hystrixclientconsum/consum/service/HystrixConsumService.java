package com.zhouhc.hystrixclientconsum.consum.service;

import com.zhouhc.hystrixclientconsum.consum.pojo.User;

import java.util.List;

public interface HystrixConsumService {

    public String startHystrixConsum(String id);

    public User findUserAnnotationSynchronize(String id);

    public User findUserAnnotationAsynchronous(String id);

    public User findUserSynchronizeCommand(String id);

    public User UserAsynchronousCommand(String id);

    public List<User> findUserObservableCommand(String id);

    public List<User> findUserToObservableCommand(String id);

    public List<User> findUserObservableAnnotationCommand(String id);

    public List<User> findUserCollapseCommand(String id);

    public List<User> findUserAnnotationCollapseCommand(String id);

    public List<User> findUserAnnotationCache(String id);

}
