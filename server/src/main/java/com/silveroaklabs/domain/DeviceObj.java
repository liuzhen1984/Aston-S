package com.silveroaklabs.domain;

import java.io.Serializable;

public class DeviceObj implements Serializable {
    private int objectIdentifier;
    private String deviceName;
    private String description;
    private String firmwareVersion;
    private String softwareVersion;
    private int protocolVersion;
    private int maxApudLength;
    private long createTime;
    private String sn;
    private String mac;
    private int userID;
    private int houseID;
    private int isRegister; //是否注册   0,1
    private long registerTime;
    private int isBind;
    private long bindTime;
    private String deviceKey;


    public int getRegister() {
        return isRegister;
    }

    public void setRegister(int register) {
        isRegister = register;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public int getObjectIdentifier() {
        return objectIdentifier;
    }

    public void setObjectIdentifier(int objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getMaxApudLength() {
        return maxApudLength;
    }

    public void setMaxApudLength(int maxApudLength) {
        this.maxApudLength = maxApudLength;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getBind() {
        return isBind;
    }

    public void setBind(int bind) {
        isBind = bind;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getBindTime() {
        return bindTime;
    }

    public void setBindTime(long bindTime) {
        this.bindTime = bindTime;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
