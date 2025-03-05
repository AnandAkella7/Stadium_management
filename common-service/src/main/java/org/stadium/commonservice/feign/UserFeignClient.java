package org.stadium.commonservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.stadium.commonservice.dto.UserDetailsDto;

@FeignClient(name = "user-service",
            url = "${user-service.url}",
            configuration = FeignSecurityConfiguration.class)
public interface UserFeignClient {
    @GetMapping("/api/internal/users/{email}")
    UserDetailsDto getUserByEmail(@PathVariable String email);

}
