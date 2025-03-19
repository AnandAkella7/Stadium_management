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
            // Always add the X-Internal-Request header for internal calls
            template.header("X-Internal-Request", "true");

             // If authenticated, also add the Authorization header
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.getCredentials()!=null) {
                template.header("Authorization", "Bearer " + authentication.getCredentials());
            }
        };

}
}
