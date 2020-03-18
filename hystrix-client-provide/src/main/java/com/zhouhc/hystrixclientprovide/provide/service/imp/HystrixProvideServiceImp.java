package com.zhouhc.hystrixclientprovide.provide.service.imp;

import com.zhouhc.hystrixclientprovide.provide.pojo.User;
import com.zhouhc.hystrixclientprovide.provide.service.HystrixProvideService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HystrixProvideServiceImp implements HystrixProvideService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String conntection(String id) {
        return id;
    }

    @Override
    public User findUserById(String id) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.equals(id, "0"))
            return new User("defalut", "defalut", "defalut");

        logger.info("---------开始执行");
        return new User(id, id, id);
    }


    @Override
    public List<User> findUserAll(String ids) {
        String[] idsTemp = StringUtils.split(ids, ",");
        List<User> User = new ArrayList<User>();
        for (String id : idsTemp) {
            logger.info("合并请求的执行:"+id);
            User.add(new User(id, id, id));
        }
        return User;
    }
}
