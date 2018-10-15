package com.shadrachjabonir.rpcframeworkexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class FrameworkConfig {

    @Bean
    @Lazy
    public static DirectServerConfig directServerConfig(){
        return new DirectServerConfig();
    }
}
