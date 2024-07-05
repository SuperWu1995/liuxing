package com.liuxing.order.service.impl;

import com.liuxing.order.client.UserClient;
import com.liuxing.order.mapper.OrderMapper;
import com.liuxing.order.pojo.Order;
import com.liuxing.order.pojo.User;
import com.liuxing.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserClient userClient;

    @Override
    public Order getById(long id) {
        Order order = orderMapper.getById(id);
        User user = userClient.getUserById(order.getUserId());
        order.setUser(user);
        return order;
    }
}
