package com.silveroaklabs.controller;

import com.silveroaklabs.service.shap.impl.HttpApduServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-28
 * Time: 下午8:09
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class MainController {
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private HttpApduServiceImpl apduService;

    @RequestMapping(value="/")
    public @ResponseBody String request(@RequestBody String data) {
//        ApplicationContext cxt = new FileSystemXmlApplicationContext(new String("F:\\my\\Shasta\\server\\src\\main\\resources\\spring-beans.xml"));
//        IShapApduService apduService = (IShapApduService) cxt.getBean("httpApduService");
          return apduService.handleApdu(data);
        }


}