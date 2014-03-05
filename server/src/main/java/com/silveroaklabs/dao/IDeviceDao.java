package com.silveroaklabs.dao;

import com.silveroaklabs.domain.DeviceObj;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public interface IDeviceDao {

    List<DeviceObj> find(Map<String, Object> where);

    DeviceObj findOne(String sn);

    int save(DeviceObj deviceObj)throws Exception;


    String register(int houseid, String sn);

    boolean bind(int userid, int houseid, String sn);
}
