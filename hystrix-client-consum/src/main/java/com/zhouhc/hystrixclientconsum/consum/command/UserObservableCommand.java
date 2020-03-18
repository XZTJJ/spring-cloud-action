package com.zhouhc.hystrixclientconsum.consum.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.zhouhc.hystrixclientconsum.consum.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.TimeUnit;

//使用响应式的开发，一次性返回多条数据
public class UserObservableCommand extends HystrixObservableCommand<User> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //缓存命令
    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("UserObservableCommand");

    private RestTemplate restTemplate;
    private String[] ids;

    public UserObservableCommand(RestTemplate restTemplate, String[] ids) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserCommandGroup")).andCommandKey(GETTER_KEY));
        this.restTemplate = restTemplate;
        this.ids = ids;
    }

    @Override
    protected Observable<User> construct() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    //表示没有取消
                    if (!subscriber.isUnsubscribed()) {
                        for (String id : ids) {
                            User userObject = restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);
                            //模拟网络故障
                            //TimeUnit.MILLISECONDS.sleep(3000);
                            //表示进行下一个的操作
                            subscriber.onNext(userObject);
                        }
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    logger.error("聚合时发生错误");
                }
            }
        });
    }

    //降级服务
    @Override
    protected Observable<User> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    //表示没有取消
                    if (!subscriber.isUnsubscribed()) {

                        User userObject = new User("UserObservableCommand", "UserObservableCommand", "UserObservableCommand");
                        //表示进行下一个的操作
                        subscriber.onNext(userObject);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    logger.error("聚合时发生错误");
                }
            }
        });
    }

    //使用缓存
    @Override
    protected String getCacheKey() {
        String idsKey = "";
        for (String id : ids)
            idsKey += id;
        return String.valueOf(idsKey);
    }

    //缓存清除
    public static  void flushCache(String id){
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(id);
    }
}
