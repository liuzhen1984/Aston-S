package com.shasta.domain;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午7:12
 * To change this template use File | Settings | File Templates.
 */
public class ShastaObj {
    private DeviceObj deviceObj;
    private long userID;
    private long houseID;

    public DeviceObj getDeviceObj() {
        return deviceObj;
    }

    public void setDeviceObj(DeviceObj deviceObj) {
        this.deviceObj = deviceObj;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getHouseID() {
        return houseID;
    }

    public void setHouseID(long houseID) {
        this.houseID = houseID;
    }
}
