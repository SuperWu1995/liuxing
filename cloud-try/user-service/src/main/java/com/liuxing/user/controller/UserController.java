package com.liuxing.user.controller;
import com.liuxing.user.pojo.User;
import com.liuxing.user.service.UserService;
import com.liuxing.user.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/{id}")
   public User getById(@PathVariable("id") long id) {
        User user = new User();
        /*
        判断Redis里是否存在该对象，存在直接返回，不存在则查库并存储在Redis里
         */
        if (Boolean.FALSE.equals(redisTemplate.boundHashOps("user").hasKey(String.valueOf(id)))) {
            user = userService.getById(id);
            redisTemplate.boundHashOps("user").put(String.valueOf(id), JsonUtil.object2Json(user));
            /*
            设置key失效时间   TimeUnit.SECONDS表示妙
             */
//            redisTemplate.boundHashOps("user").getOperations().expire(String.valueOf(id),getTime(),TimeUnit.SECONDS);
//           System.out.println(time+": "+ redisTemplate.boundHashOps("user").get(String.valueOf(id)));

            redisTemplate.boundHashOps("user").expire(5, TimeUnit.SECONDS); //只能设置user集合的过期时间，无法设置单个元素的过期时间
        } else {
            user =(User) JsonUtil.json2obj((String) redisTemplate.boundHashOps("user").get(String.valueOf(id)),User.class);
        }
//        if (redisTemplate.opsForValue().get(String.valueOf(id))==null) {
//            user = userService.getById(id);
//            redisTemplate.opsForValue().set(String.valueOf(id),user);
//        } else {
//            user =(User) redisTemplate.opsForValue().get(String.valueOf(id));
//        }
        return user;
    }

    static int time = 0;

    private  int getTime(){
        time=5;
        return time;
    }
}
