package org.stadium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class}
)
@EnableJpaRepositories(basePackages = "org.stadium.userservice.repository")
@EntityScan(basePackages = "org.stadium.userservice")
@EnableFeignClients(basePackages = "org.stadium.commonservice.feign")
public class UserServiceApplication {
    public static void main(String[] args) {
       ConfigurableApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);
        System.out.println("user-service.url = " + context.getEnvironment().getProperty("user-service.url"));
    }
}