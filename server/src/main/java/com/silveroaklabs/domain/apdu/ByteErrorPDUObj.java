package com.silveroaklabs.domain.apdu;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-2
 * Time: 上午12:13
 * To change this template use File | Settings | File Templates.
 */
public class ByteErrorPDUObj {
    private int type;    //0，1，2，3，4 ShapConstants
    private int invokeID;   // 取值范围 0-255，由服务请求方赋值。回复 PDU 中的 Original Invoke ID 字段必须和请求
                              // 的相同，用于关联请求和回复。
                              // Unconfirmed-Req PDU不需要该植
    private int serviceType; // SHAPServiceType 类型
    private int errorClass; //只有4存在
    private int errorCode; //只有4存在
    private byte[] data;    //请求数据  Simple-Ack PDU为空

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(int errorClass) {
        this.errorClass = errorClass;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
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

    public int getInvokeID() {
        return invokeID;
    }

    public void setInvokeID(int invokeID) {
        this.invokeID = invokeID;
    }

}
