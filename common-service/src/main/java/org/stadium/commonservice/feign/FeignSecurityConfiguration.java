package org.stadium.commonservice.feign;

import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Bean;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignSecurityConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return template -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.getCredentials()!=null) {
                template.header("X-Internal-Request", "true");
                template.header("Authorization", "Bearer " + authentication.getCredentials());
            }
        };

}
}
