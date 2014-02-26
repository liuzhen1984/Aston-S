package com.shasta.controller;

import com.shasta.domain.DeviceObj;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value="/device")
public class DeviceController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public @ResponseBody DeviceObj get(@PathVariable("id") Long id) {
        return new DeviceObj();
    }

    @RequestMapping(value="/{sn}/register",method= RequestMethod.GET)
    public @ResponseBody
    String register(@PathVariable("sn") String sn) {
        return new String("range_str");
    }
    @RequestMapping(value="/{id}/push",method= RequestMethod.POST)
    public @ResponseBody DeviceObj pushData(@PathVariable("id") Long id,
                                            @RequestParam("session_key") String key,
                                            @RequestParam("data") String data,

                                            @RequestParam("sn") String sn) {

        //redis中针对该IP过来请求错误次数,大于5次则直接return

        //redis中对比session_key ，redis不存在，则取数据获取

        //对比是否正确 ，错误的话返回，并计数

        //如果有config更新，session_key更新直接返回，否则返回默认值
        return new DeviceObj();
    }
}