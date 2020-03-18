package com.zhouhc.hystrixclientconsum.consum.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.zhouhc.hystrixclientconsum.consum.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

public class UserAsynchronousCommand extends HystrixCommand<User> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //指定程序的命令名称
    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("UserAsynchronousCommandKey");

    private RestTemplate restTemplate;
    private String id;

    public UserAsynchronousCommand(RestTemplate restTemplate, String id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserCommandGroup")).andCommandKey(GETTER_KEY));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    //使用异步的方式同进行开发,都是放在run里面的
    @Override
    protected User run() {
        User userObject = null;
        try {
            //模拟网络故障
            //TimeUnit.MILLISECONDS.sleep(3000);
            userObject = restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);
        } catch (Exception e) {
            logger.error("访问出现错误", e);
        }
        return userObject;
    }

    //降级服务
    @Override
    protected User getFallback() {
        return new User("UserAsynchronousCommand", "UserAsynchronousCommand", "UserAsynchronousCommand");
    }

    //缓存服务
    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    //清理缓存
    public static void flushCache(String id) {
        //通过命令Key，使用默认的并发策略进行清除缓存
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));
    }
}
