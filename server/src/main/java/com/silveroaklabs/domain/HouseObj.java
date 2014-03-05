package com.silveroaklabs.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午7:07
 * To change this template use File | Settings | File Templates.
 */
public class HouseObj {
    private int  countryID;
    private int stateID;
    private int cityID;
    private String street;
    private String number;
    private int houseType;
    private List<DeviceObj> devices;  /*当列表为空应该被清理掉*/

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getHouseType() {
        return houseType;
    }

    public void setHouseType(int houseType) {
        this.houseType = houseType;
    }

    public List<DeviceObj> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceObj> devices) {
        this.devices = devices;
    }
}
