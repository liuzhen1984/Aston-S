package com.silveroaklabs.service.shap.impl;

import com.silveroaklabs.constant.ShapConstants;
import com.silveroaklabs.domain.apdu.ShapPDUObj;
import com.silveroaklabs.service.shap.IShapApduService;
import org.springframework.stereotype.Service;

@Service("stdApduService")
public class StdApduServiceImpl implements IShapApduService {
    public StdApduServiceImpl(){
        this.shapPDUObj.setVde(this.vde);
    }

    private final int vde = ShapConstants.Vde.BINARY;//0 json ; 1 byte
    private ShapPDUObj shapPDUObj = new ShapPDUObj();

    @Override
    public byte[] encodeConfirmedReq(ShapPDUObj byteConfirmeReqPDUObj) throws Exception {
        byte apdu[] = new byte[byteConfirmeReqPDUObj.getDataByte().length+3];
        apdu[0] = (byte)(vde & 3);
        apdu[1] = (byte)byteConfirmeReqPDUObj.getInvokeID();
        apdu[2] = (byte)byteConfirmeReqPDUObj.getServiceType();
        System.arraycopy(byteConfirmeReqPDUObj.getDataByte(), 0, apdu, 3, byteConfirmeReqPDUObj.getDataByte().length);
        return apdu;
    }

    @Override
    public byte[] encodeUnconfirmedReq(ShapPDUObj byteUnconfirmeReqPDUObj) throws Exception {
        if (byteUnconfirmeReqPDUObj.getDataByte() != null) {
            byte apdu[] = new byte[byteUnconfirmeReqPDUObj.getDataByte().length+2];
            apdu[0] = (byte)(0x10 | (vde & 3));
            apdu[1] = (byte)byteUnconfirmeReqPDUObj.getServiceType();
            System.arraycopy(byteUnconfirmeReqPDUObj.getDataByte(), 0, apdu, 2, byteUnconfirmeReqPDUObj.getDataByte().length);
            return apdu;
        }
        else {
            byte apdu[] = new byte[2];
            apdu[0] = (byte)(0x10 | (vde & 3));
            apdu[1] = (byte)byteUnconfirmeReqPDUObj.getServiceType();
            return apdu;
        }
    }
    @Override
    public byte[] encodeSimpleAck(ShapPDUObj simpleAckPDUObj) throws Exception {
        byte apdu[] = new byte[3];
        apdu[0] = (byte)(0x20 | (vde & 3));
        apdu[1] = (byte)simpleAckPDUObj.getInvokeID();
        apdu[2] = (byte)simpleAckPDUObj.getServiceType();
        return apdu;
    }

    @Override
    public byte[] encodeComplexAck(ShapPDUObj byteComplexAckPDUObj) {
        byte apdu[] = new byte[byteComplexAckPDUObj.getDataByte().length+3];
        apdu[0] = (byte)(0x30 | (vde & 3));
        apdu[1] = (byte)byteComplexAckPDUObj.getInvokeID();
        apdu[2] = (byte)byteComplexAckPDUObj.getServiceType();
        System.arraycopy(byteComplexAckPDUObj.getDataByte(), 0, apdu, 3, byteComplexAckPDUObj.getDataByte().length);
        return apdu;
    }
    @Override
    public byte[] encodeError(ShapPDUObj byteErrorPDUObj) {
        byte apdu[] = new byte[(byteErrorPDUObj.getDataByte()!=null?byteErrorPDUObj.getDataByte().length:0)+5];
        apdu[0] = (byte)(0x40 | (vde & 3));
        apdu[1] = (byte)byteErrorPDUObj.getInvokeID();
        apdu[2] = (byte)byteErrorPDUObj.getServiceType();
        apdu[3] = (byte)byteErrorPDUObj.getErrorClass();
        apdu[4] = (byte)byteErrorPDUObj.getErrorCode();
        if (byteErrorPDUObj.getDataByte() != null)
            System.arraycopy(byteErrorPDUObj.getDataByte(), 0, apdu, 5, byteErrorPDUObj.getDataByte().length);
        return apdu;
    }
    public int getVDE(){
        return this.vde;
    }

    public ShapPDUObj decode(String data) {
        return null;
    }

    public String handleApdu(String constant){
        return null;
    }
}
