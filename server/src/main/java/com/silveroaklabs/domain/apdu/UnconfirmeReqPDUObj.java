package com.silveroaklabs.domain.apdu;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-2
 * Time: 上午12:13
 * To change this template use File | Settings | File Templates.
 */
public class UnconfirmeReqPDUObj {
    private int type;    //0，1，2，3，4 ShapConstants
    private int serviceType; // SHAPServiceType 类型
    private String data;    //请求数据  Simple-Ack PDU为空

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
