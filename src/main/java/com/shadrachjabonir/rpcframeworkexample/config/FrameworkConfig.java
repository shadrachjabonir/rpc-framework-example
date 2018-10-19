package com.shadrachjabonir.rpcframeworkexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class FrameworkConfig {

    @Bean
    public static DirectServerConfig directServerConfig() {
        return new DirectServerConfig();
    }

    @Bean
    public static QueueServerConfig queueServerConfig() {
        return new QueueServerConfig();
    }
}
