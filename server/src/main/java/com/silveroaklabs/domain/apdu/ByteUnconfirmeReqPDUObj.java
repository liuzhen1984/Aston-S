package com.silveroaklabs.domain.apdu;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-2
 * Time: 上午12:13
 * To change this template use File | Settings | File Templates.
 */
public class ByteUnconfirmeReqPDUObj {
    private int type;    //0，1，2，3，4 ShapConstants
    private int serviceType; // SHAPServiceType 类型
    private byte[] data;    //请求数据  Simple-Ack PDU为空

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
