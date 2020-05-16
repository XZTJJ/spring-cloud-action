package com.example.cloudconfigclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//从配置中心获取配置信息
@RefreshScope
@RestController
public class ConfigControllerV2 {

    @Autowired
    private Environment env;

    @RequestMapping("/cloud-config-clientV2")
    public String getcloudConfigClient(){
        String valueString = env.getProperty("cloud-config-client","error");
        valueString += " , " + env.getProperty("cloud-config-jiam","error");
        return valueString;
    }
}
