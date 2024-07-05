package com.liuxing.order.service;

import com.liuxing.order.pojo.Order;
import org.springframework.stereotype.Service;

public interface OrderService {
    Order getById( long id);
}
