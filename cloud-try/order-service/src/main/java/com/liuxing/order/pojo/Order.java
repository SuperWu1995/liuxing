package com.liuxing.order.pojo;

import lombok.Data;

import java.util.Objects;

@Data
public class Order {
    private long id;
    private long price;
    private String name;
    private Integer num;
    private long userId;
    private User user;

    public Order(){}

    public Order(long id, long price, String name, Integer num, long userId, User user) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.num = num;
        this.userId = userId;
        this.user = user;
    }
}
