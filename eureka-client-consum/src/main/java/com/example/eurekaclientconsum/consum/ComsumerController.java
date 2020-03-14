package com.example.eurekaclientconsum.consum;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ComsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/ribbonConsumer")
    public String RPCHello() {
        String body = restTemplate.getForEntity("http://EUREKA-CLIENT-PROVIDE/hello", String.class).getBody();
        return body;
    }
}
