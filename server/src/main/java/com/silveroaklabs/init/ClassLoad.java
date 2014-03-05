package com.silveroaklabs.init;

import com.silveroaklabs.constant.AttributeConstants;
import com.silveroaklabs.dao.IDeviceDao;
import com.silveroaklabs.domain.DeviceObj;
import com.silveroaklabs.service.IShapService;
import com.silveroaklabs.service.impl.DeviceServiceImpl;
import com.silveroaklabs.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 上午9:56
 * To change this template use File | Settings | File Templates.
 */
@Service("classLoad")
public class ClassLoad <T> {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DeviceServiceImpl deviceService;

    public static Map<Integer,IShapService> InvokeClassMap=new HashMap<Integer, IShapService>();
    static{
        ApplicationContext ac = new FileSystemXmlApplicationContext("F:\\my\\Shasta\\server\\cloudserver\\src\\main\\webapp\\WEB-INF\\rest-servlet.xml");
        ClassLoad classLoad = (ClassLoad) ac.getBean("classLoad");
// /
//  IUserServiceImpl
        InvokeClassMap.put(classLoad.userService.getObjectIdentifier(),classLoad.userService);
        InvokeClassMap.put(classLoad.deviceService.getObjectIdentifier(),classLoad.deviceService);

        //加载设备sessionKey
        IDeviceDao deviceDao = (IDeviceDao) ac.getBean("deviceDao");
        Map<String,Object> whereMap = new HashMap<String,Object>();

        whereMap.put("is_register", AttributeConstants.IS_REGISTER.YES);

        List<DeviceObj> deviceObjList =  deviceDao.find(whereMap);
        for(DeviceObj deviceObj : deviceObjList){
            SimulateRedis.DeviceDB.put(deviceObj.getDeviceKey(),deviceObj.getSn());
        }
    }

}
