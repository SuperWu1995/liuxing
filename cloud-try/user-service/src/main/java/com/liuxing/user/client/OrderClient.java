package com.liuxing.user.client;
import com.liuxing.user.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@FeignClient(value = "orderservice")
public interface OrderClient {
    @GetMapping("/order/{Id}")
    ArrayList<Order> getOrderByUserId(@PathVariable("Id") long UserId);
}
