package com.silveroaklabs.service;

import com.silveroaklabs.domain.Result;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public interface IDeviceService {
    String getDevices();

    Result getDevices(String sessionKey);

    Result getDevice(String sn);

    Result register(String sn,String mac);

    Result enableBinding(String sn);

    Result getDevicesFromUser(String sessionKey);
}
