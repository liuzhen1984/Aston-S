package com.shasta.controller;

import com.shasta.domain.UserObj;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-24
 * Time: 下午11:07
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public @ResponseBody
    UserObj get(@PathVariable("id") Long id) {
        return new UserObj();
    }
    @RequestMapping(value="/register",method= RequestMethod.GET)
    public @ResponseBody
    UserObj register(@RequestParam("username") String username,
                     @RequestParam("email") String email,
                     @RequestParam("password") String password,
                     @RequestParam("rePassword") String rePassword) {
        //对比两次密码是否正确


        //基本数据验证,email，username,等通信方式是否有重复的

        //添加到数据中

        return new UserObj();
    }
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public @ResponseBody UserObj login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        //验证密码是否正确

        //正确后，添加到redis中表示已经登录
        return new UserObj();
    }

}
