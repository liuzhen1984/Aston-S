package com.silveroaklabs.init;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午9:57
 * To change this template use File | Settings | File Templates.
 */
public class SimulateRedis {
    //sessionKey : userID
    public static Map<String,Integer> UserDB = new HashMap<String,Integer>();

    //sessionKey: device_sn
    public static Map<String,String> DeviceDB = new HashMap<String, String>();



    //rangNum : device_sn
    public static Map<String,String> BindDeviceDB = new HashMap<String, String>();

}
