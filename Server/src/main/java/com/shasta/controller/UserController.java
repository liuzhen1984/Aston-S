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
    @RequestMapping(value="/{id}/register",method= RequestMethod.GET)
    public @ResponseBody
    UserObj register(@PathVariable("id") Long id) {
        return new UserObj();
    }
    @RequestMapping(value="/{id}/push",method= RequestMethod.POST)
    public @ResponseBody UserObj register(@PathVariable("id") Long id,
                                            @RequestParam("key") String key) {
        return new UserObj();
    }
}
