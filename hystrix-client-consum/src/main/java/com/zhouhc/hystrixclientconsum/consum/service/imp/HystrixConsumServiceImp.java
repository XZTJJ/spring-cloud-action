package com.zhouhc.hystrixclientconsum.consum.service.imp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.zhouhc.hystrixclientconsum.consum.command.*;
import com.zhouhc.hystrixclientconsum.consum.pojo.User;
import com.zhouhc.hystrixclientconsum.consum.service.HystrixConsumService;

import com.zhouhc.hystrixclientconsum.consum.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

@Service
public class HystrixConsumServiceImp implements HystrixConsumService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    //用于合并请求的执行
    @Autowired
    private UserService userService;

    //使用服务降级功能
    @HystrixCommand(fallbackMethod = "startHystrixConsumBack")
    @Override
    public String startHystrixConsum(String id) {
        String forObject = restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/connection?id={1}", String.class, id);
        return forObject;
    }

    //回调方法的降级
    public String startHystrixConsumBack(String id) {
        return "this is a error message";
    }

    //使用command的注解同步方式进行开发
    @HystrixCommand(fallbackMethod = "findUserConsumBack")
    @Override
    public User findUserAnnotationSynchronize(String id) {
        User userObject = restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);
        return userObject;
    }

    //使用command的注解异步方式进行开发
    @HystrixCommand(fallbackMethod = "findUserConsumBack")
    @Override
    public User findUserAnnotationAsynchronous(final String id) {
        User user = null;
        try {
            long startTime = System.currentTimeMillis();
            Future<User> userFuture = new AsyncResult<User>() {
                @Override
                public User invoke() {
                    return restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);
                }

                //因为后面需要调用他，所以要重写get()方法，不然会报错
                @Override
                public User get() {
                    return invoke();
                }
            };
            logger.info("---------注解异步方式:查看是否是异步执行的");
            user = userFuture.get();
            long endTime = System.currentTimeMillis();
            logger.info("---------注解异步方式:耗时为:" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("错误", e);
        }
        return user;
    }

    //服务降级
    public User findUserConsumBack(String id) {
        return new User("error", "error", "error");
    }

    //使用同步的方式
    public User findUserSynchronizeCommand(String id) {
        UserSynchronizeCommand userSynchronizeCommand = new UserSynchronizeCommand(restTemplate, id + "");
        User execute = userSynchronizeCommand.execute();
        logger.info("---------同步方式:查看是否是同步执行的");
        return execute;
    }

    //使用异步的方式
    public User UserAsynchronousCommand(String id) {
        User user = null;
        try {
            UserAsynchronousCommand userAsynchronousCommand = new UserAsynchronousCommand(restTemplate, id + "");
            Future<User> userFuture = userAsynchronousCommand.queue();
            long startTime = System.currentTimeMillis();
            logger.info("---------异步方式:查看是否是异步执行的");
            user = userFuture.get();
            long endTime = System.currentTimeMillis();
            logger.info("---------异步方式:耗时为:" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("错误", e);
        }
        return user;
    }


    //使用HystrixObservableCommand，进行多条返回
    public List<User> findUserObservableCommand(String id) {
        List<User> userList = new ArrayList<User>();
        try {
            long startTime = System.currentTimeMillis();
            logger.info("---------响应方式:查看是否是响应执行的");
            //创建一个对象
            String[] ids = StringUtils.split(id, "-");
            UserObservableCommand userObservableCommand = new UserObservableCommand(restTemplate, ids);
            //进行返回
            Observable<User> observe = userObservableCommand.observe();
            //进行事件源的处理
            observe.subscribe(new Observer<User>() {
                @Override
                public void onCompleted() {
                    logger.info("请求处理完成");
                }

                @Override
                public void onError(Throwable e) {
                    logger.error("请求处理错误", e);
                }

                @Override
                public void onNext(User user) {
                    userList.add(user);
                }
            });

            long endTime = System.currentTimeMillis();
            logger.info("---------响应方式:耗时为:" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("错误", e);
        }
        return userList;
    }

    //使用HystrixObservableCommand，进行多条返回
    public List<User> findUserToObservableCommand(String id) {
        List<User> userList = new ArrayList<User>();
        try {
            long startTime = System.currentTimeMillis();
            logger.info("---------TO响应方式:查看是否是响应执行的");
            //创建一个对象
            String[] ids = StringUtils.split(id, "-");
            UserToObservableCommand userObservableCommand = new UserToObservableCommand(restTemplate, ids);
            //进行返回
            Observable<User> observe = userObservableCommand.toObservable();
            //进行事件源的处理
            observe.subscribe(new Observer<User>() {
                @Override
                public void onCompleted() {
                    logger.info("请求处理完成");
                }

                @Override
                public void onError(Throwable e) {
                    logger.error("请求处理错误", e);
                }

                @Override
                public void onNext(User user) {
                    userList.add(user);
                }
            });

            long endTime = System.currentTimeMillis();
            logger.info("---------TO响应方式:耗时为:" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("错误", e);
        }
        return userList;
    }

    //使用HystrixObservableCommand，进行多条返回，注解的方式
    @HystrixCommand(fallbackMethod = "startHystrixConsumBack")
    public Observable<User> getObservableObj(String id) {
        final String[] ids = StringUtils.split(id, "-");
        //创建对象
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    //表示没有取消
                    if (!subscriber.isUnsubscribed()) {
                        for (String id : ids) {
                            User userObject = restTemplate.getForObject("http://HYSTRIX-CLIENT-PROVIDE/hystrix/findUser?id={1}", User.class, id);
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

    public List<User> findUserObservableAnnotationCommand(String id) {
        final List<User> usersList = new ArrayList<User>();
        long startTime = System.currentTimeMillis();
        logger.info("---------响应注解方式:查看是否是响应执行的");
        Observable<User> observe = getObservableObj(id);
        //进行事件源的处理
        observe.subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
                logger.info("请求处理完成");
            }

            @Override
            public void onError(Throwable e) {
                logger.error("请求处理错误", e);
            }

            @Override
            public void onNext(User user) {
                usersList.add(user);
            }
        });

        long endTime = System.currentTimeMillis();
        logger.info("---------响应注解方式:耗时为:" + (endTime - startTime));

        return usersList;
    }

    //使用UserCollapseCommand进行请求的合并操作
    @Override
    public List<User> findUserCollapseCommand(String id) {
        List userList = new ArrayList();
        long startTime = System.currentTimeMillis();
        logger.info("---------合共请求方式:查看是否是合并请求执行的");
        //模拟多次请求
        String[] idTemp = StringUtils.split(id, "-");
        //保存请求结果
        List<Future<User>> futureLists = new ArrayList<Future<User>>();
        for (String idItem : idTemp) {
            Future<User> userFuture = new UserCollapseCommand(userService, idItem).queue();
            futureLists.add(userFuture);
        }
        //获取结果
        for (Future<User> userFuture : futureLists) {
            try {
                Object user = userFuture.get();
                userList.add(user);
            } catch (Exception e) {
                logger.error("获取结果错误", e);
            }
        }

        //计时
        long endTime = System.currentTimeMillis();
        logger.info("---------合共请求方式:耗时为:" + (endTime - startTime));
        return userList;
    }

    //使用HystrixCollapse进行请求的合并操作，注解的方式
    @Override
    public List<User> findUserAnnotationCollapseCommand(String id) {
        List userList = new ArrayList();
        long startTime = System.currentTimeMillis();
        logger.info("---------合共请求方式:查看是否是合并请求执行的");
        //模拟多次请求
        String[] idTemp = StringUtils.split(id, "-");
        //保存请求结果
        List<Future<User>> futureLists = new ArrayList<Future<User>>();
        for (String idItem : idTemp) {
            User userAnnotation = userService.findUserAnnotation(idItem);
            userList.add(userAnnotation);
            System.out.println("返回的结果为:" + userAnnotation);
        }
        //计时
        long endTime = System.currentTimeMillis();
        logger.info("---------合共请求方式:耗时为:" + (endTime - startTime));
        return userList;
    }

    //使用注解时的缓存
    @Override
    public List<User> findUserAnnotationCache(String id) {
        List userList = new ArrayList();
        long startTime = System.currentTimeMillis();
        logger.info("---------注解緩存方式:查看是否是注解緩请求执行的");
        //模拟多次请求
        User userAnnotation1 = userService.findUserAnnotationCache(id);
        userService.flushCache(id);
        User userAnnotation2 = userService.findUserAnnotationCache(id);
        User userAnnotation3 = userService.findUserAnnotationCache(id);

        userList.add(userAnnotation1);
        userList.add(userAnnotation2);
        userList.add(userAnnotation3);
        //计时
        long endTime = System.currentTimeMillis();
        logger.info("---------注解緩存方式:耗时为:" + (endTime - startTime));
        return userList;
    }
}
