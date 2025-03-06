package org.stadium.userservice.userconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BulkheadConfigOverride {
    
    @Bean
    @Primary
    public io.github.resilience4j.common.bulkhead.configuration.CommonThreadPoolBulkheadConfigurationProperties 
            primaryThreadPoolBulkheadProperties(
                io.github.resilience4j.springboot3.bulkhead.autoconfigure.ThreadPoolBulkheadProperties properties) {
        return properties;
    }
}
