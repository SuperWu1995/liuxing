package com.liuxing.order.client;

import com.liuxing.order.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userservice")
public interface UserClient {
    @GetMapping("/user/{UserId}")
    User getUserById(@PathVariable("UserId") long UserId);
}
