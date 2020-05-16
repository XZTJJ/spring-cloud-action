package com.example.cloudconfigclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//从配置中心获取配置信息
@RefreshScope
@RestController
public class ConfigControllerV1 {
    @Value("${cloud-config-client}")
    private String cloud_config_client;

    @RequestMapping("/cloud-config-client")
    public String getcloudConfigClient(){
        return  this.cloud_config_client;
    }
}
