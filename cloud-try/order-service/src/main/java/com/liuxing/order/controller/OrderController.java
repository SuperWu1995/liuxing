package com.liuxing.order.controller;

import com.liuxing.order.pojo.Order;
import com.liuxing.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private  OrderService orderService;
    @GetMapping("/{id}")
   public Order getById(@PathVariable("id") long id) {
        Order order = orderService.getById(id);
        return order;
    }
}
