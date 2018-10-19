package com.shadrachjabonir.rpcframeworkexample.config;

import com.shadrachjabonir.rpcframeworkexample.service.TestQueueService;
import com.shadrachjabonir.rpcframeworkexample.service.impl.TestQueueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueServiceListConfig {
    @Bean
    TestQueueService testQueueService(){
        return new TestQueueServiceImpl();
    }
}
