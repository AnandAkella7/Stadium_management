package org.stadium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.stadium.eventservice.repository")
@EntityScan(basePackages = "org.stadium.eventservice")
@EnableFeignClients(basePackages = "org.stadium.commonservice.feign")
public class EventServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EventServiceApplication.class, args);
        System.out.println("event-service.url = " + context.getEnvironment().getProperty("event-service.url"));
    }
}