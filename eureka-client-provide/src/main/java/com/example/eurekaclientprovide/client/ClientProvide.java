package com.example.eurekaclientprovide.client;


import com.sun.java.swing.plaf.windows.WindowsToolBarSeparatorUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientProvide {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/hello")
    public String index(){
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        LOG.info("/hello,host:"+ instance.getHost()+",server_id:"+instance.getServiceId());
        return "sucess";
    }
}
