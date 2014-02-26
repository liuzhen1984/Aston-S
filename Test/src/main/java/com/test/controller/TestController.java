package com.test.controller;

import com.test.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午11:01
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class TestController  {
    @RequestMapping({"/get","/"})
    public String test(){
        User user  = new User();
        user.setName("springmvc") ;
        System.out.println("tesetadsfadfa");

        return "default";
    }
}
