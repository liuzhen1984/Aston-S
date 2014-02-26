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

    @RequestMapping(value="/{id}/register",method= RequestMethod.GET)
    public @ResponseBody
    DeviceObj register(@PathVariable("id") Long id) {
        return new DeviceObj();
    }
    @RequestMapping(value="/{id}/push",method= RequestMethod.POST)
    public @ResponseBody DeviceObj pushData(@PathVariable("id") Long id,
                                            @RequestParam("key") String key) {
        return new DeviceObj();
    }
}