package com.silveroaklabs.domain.apdu;

import com.silveroaklabs.constant.ShapConstants;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-2
 * Time: 上午12:13
 * To change this template use File | Settings | File Templates.
 */
public class ShapPDUObj {
    private int type;    //0，1，2，3，4 ShapConstants
    private int vde;    //指明可变数据部分编码格式。0 为 JSON 对象格式编码，1 为二进
    //制流编码。一般来说，只有文件读写服务时 VDE 为 1，其他服务时 VDE 为 0。
    private int invokeID;   // 取值范围 0-255，由服务请求方赋值。回复 PDU 中的 Original Invoke ID 字段必须和请求
    // 的相同，用于关联请求和回复。
    // Unconfirmed-Req PDU不需要该植
    private int serviceType; // SHAPServiceType 类型
    private int errorClass; //只有4存在
    private int errorCode; //只有4存在
    private int error;// 通过errorCode,errorClass 产生的；
    private Object data;    //请求数据  Simple-Ack PDU为空

    public int getVde() {
        return vde;
    }

    public void setVde(int vde) {
        this.vde = vde;
    }

    public int getError(){
        return (this.errorClass&0xff)<<24 | (this.errorCode&0x00ffffff);
    }
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

    public byte[] getDataByte(){
        if(this.vde== ShapConstants.Vde.BINARY){
            return (byte[]) this.data;
        }
        return null;
    }
    public String getDataString() {
        if(this.vde== ShapConstants.Vde.JSON){
            return (String) this.data;
        }
        return null;
    }
    public Object getData(){
        return this.data;
    }

    public void setData(Object data) {
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
