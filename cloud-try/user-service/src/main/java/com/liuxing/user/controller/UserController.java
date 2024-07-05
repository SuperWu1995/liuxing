package com.liuxing.user.controller;
import com.liuxing.user.pojo.User;
import com.liuxing.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
   public User getById(@PathVariable("id") long id) {
//        orderService.getById(id);
        return userService.getById(id);
    }
}
