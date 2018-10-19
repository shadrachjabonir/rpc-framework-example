package com.shadrachjabonir.rpcframeworkexample.config;

import com.shadrachjabonir.rpcframeworkexample.service.TestService;
import com.shadrachjabonir.rpcframeworkexample.service.impl.TestServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectServiceListConfig {

    @Bean
    TestService testService() {
        return new TestServiceImpl();
    }

}
