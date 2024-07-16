package com.liuxing.order.service.impl;

import com.liuxing.order.client.UserClient;
import com.liuxing.order.mapper.OrderMapper;
import com.liuxing.order.pojo.Order;
import com.liuxing.order.pojo.User;
import com.liuxing.order.service.OrderService;
import com.liuxing.order.util.IdWorker;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserClient userClient;

    @Override
    @GlobalTransactional(name = "orderGetById-{id}",rollbackFor = Exception.class)
    /*
    1.例子
    @GlobalTransactional(name = "orderGetById-{id}",rollbackFor = Exception.class)
    name = "orderGetById-{id}" 根据传入id动态定义事务名称
    rollbackFor = Exception.class   只要发生异常就回滚（Exception.class是所有异常的父类）
    2.例子
    @GlobalTransactional(name = "OrderProcessing-{#order.id}")
    {#order.id}   根据传入的对象order的id动态定义事务名称
    3.
    通常查询是不需要分布式事务的，分布式事务是用来确保数据的一致性来为查询服务的而且分布式事务很消耗性能
    对读写分离的数据库来说查询更加不需要分布式事务
     */
    public Order getById(long id) {

        Order order = orderMapper.getById(id);
        User user = userClient.getUserById(order.getUserId());
        order.setUser(user);
        return order;
    }
}
