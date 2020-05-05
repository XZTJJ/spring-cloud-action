package com.example.feignclientconsum.service;


import com.example.service.FeignService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClient;

//客户端的服务方法调用，直接通过Feign调用服务提供方的方法了
@FeignClient(name="feign-client-provide")
@Qualifier("feignConsumRefactorService")
public interface FeignConsumRefactorService extends FeignService {

}
