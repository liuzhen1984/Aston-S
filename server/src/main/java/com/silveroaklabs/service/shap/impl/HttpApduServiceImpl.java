package com.silveroaklabs.service.shap.impl;

import com.silveroaklabs.constant.ShapConstants;
import com.silveroaklabs.constant.ShapError;
import com.silveroaklabs.domain.Result;
import com.silveroaklabs.domain.apdu.ShapPDUObj;
import com.silveroaklabs.init.ClassLoad;
import com.silveroaklabs.service.IShapService;
import com.silveroaklabs.service.shap.IShapApduService;
import com.silveroaklabs.util.ShapUtil;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service("httpApduService")
public class HttpApduServiceImpl implements IShapApduService {
    private final int vde=ShapConstants.Vde.JSON;

    public HttpApduServiceImpl(){
        this.httpPDUObj.setVde(this.vde);
    }

    private ShapPDUObj httpPDUObj = new ShapPDUObj();

    public String encodeConfirmedReq(ShapPDUObj confirmeReqPDUObj) throws Exception {
        if (confirmeReqPDUObj.getDataString() == null || confirmeReqPDUObj.getDataString().isEmpty())
            return String.format("[0,%d,%d]", confirmeReqPDUObj.getInvokeID(), confirmeReqPDUObj.getServiceType());
        else
            return String.format("[0,%d,%d,%s]", confirmeReqPDUObj.getInvokeID(), confirmeReqPDUObj.getServiceType(), confirmeReqPDUObj.getDataString());
    }

    public String encodeUnconfirmedReq(ShapPDUObj unconfirmeReqPDUObj) throws Exception {
        if (unconfirmeReqPDUObj.getDataString() == null || unconfirmeReqPDUObj.getDataString().isEmpty())
            return String.format("[1,%d]", unconfirmeReqPDUObj.getServiceType());
        else
            return String.format("[1,%d,%s]", unconfirmeReqPDUObj.getServiceType(), unconfirmeReqPDUObj.getDataString());
    }

    public String encodeSimpleAck(ShapPDUObj simpleAckPDUObj) throws Exception {
         return String.format("[2,%d,%d]", simpleAckPDUObj.getInvokeID(), simpleAckPDUObj.getServiceType());
    }

    @Override
    public String encodeComplexAck(ShapPDUObj complexAckPDUObj) {
        if (complexAckPDUObj.getData() == null || complexAckPDUObj.getDataString().isEmpty())
            return String.format("[3,%d,%d]", complexAckPDUObj.getInvokeID(), complexAckPDUObj.getServiceType());
        else
            return String.format("[3,%d,%d,%s]", complexAckPDUObj.getInvokeID(), complexAckPDUObj.getServiceType(), complexAckPDUObj.getDataString());
    }

    public String encodeError(ShapPDUObj errorPDUObj) {
       return String.format("[4,%d,%d,%d,%d]", errorPDUObj.getInvokeID(), errorPDUObj.getServiceType(), errorPDUObj.getErrorClass(), errorPDUObj.getErrorCode());
    }

    public int getVDE(){
        return this.vde;
    }

    public ShapPDUObj decode(String data){
        httpPDUObj.setVde(this.vde);
        try {
            org.json.JSONArray dataJsonArray = new JSONArray(data);
            httpPDUObj.setType( dataJsonArray.getInt(0));
            switch (httpPDUObj.getType())
            {
                case ShapConstants.PduType.ConfirmedReq:
                    httpPDUObj.setInvokeID(dataJsonArray.getInt(1));
                    httpPDUObj.setServiceType(dataJsonArray.getInt(2));
                    httpPDUObj.setData(dataJsonArray.getJSONArray(3));
                    break;
                case ShapConstants.PduType.UnconfirmedReq:
                    httpPDUObj.setServiceType(dataJsonArray.getInt(1));
                    httpPDUObj.setData(dataJsonArray.getJSONArray(2));
                    break;
                case ShapConstants.PduType.SimpleAck:
                    httpPDUObj.setInvokeID(dataJsonArray.getInt(1));
                    httpPDUObj.setServiceType(dataJsonArray.getInt(2));
                    break;
                case ShapConstants.PduType.ComplexAck:
                    httpPDUObj.setInvokeID(dataJsonArray.getInt(1));
                    httpPDUObj.setServiceType(dataJsonArray.getInt(2));
                    httpPDUObj.setData(dataJsonArray.getJSONArray(3));
                    break;
                case ShapConstants.PduType.Error:
                    httpPDUObj.setInvokeID(dataJsonArray.getInt(1));
                    httpPDUObj.setServiceType(dataJsonArray.getInt(2));
                    httpPDUObj.setErrorClass(dataJsonArray.getInt(3));
                    httpPDUObj.setErrorCode(dataJsonArray.getInt(4));
                    httpPDUObj.setData(dataJsonArray.getJSONArray(5));
                    break;
                default:
                    return null;
            }
            return this.httpPDUObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String handleInvokeMethod(){
        JSONArray json = (JSONArray) this.httpPDUObj.getData();
        int len = json.length();
        int ei = -1;
        if (len < 2)
        {
            return this.encodeError(ShapUtil.transToErrorPDUString(httpPDUObj, ShapError.Class.Reject, ShapError.Code.RejectMissingRequiredParameter, null));
        }
        int objectId = json.optInt(0, -1);
        int propertyId = json.optInt(1, -1);
        if (objectId < 0 || propertyId < 0)
        {
            return this.encodeError(ShapUtil.transToErrorPDUString(httpPDUObj, ShapError.Class.Reject,ShapError.Code.RejectInvalidParameterDataType,null));
        }
        Object value = null;
        if (len >= 3)
        {
            if (!json.isNull(2))
                value = json.opt(2);
        }
        if (len >= 4)
            ei = json.optInt(3, -1);

        //根据objectId,获取实例
        if(!ClassLoad.InvokeClassMap.containsKey(objectId)){
            return this.encodeError(ShapUtil.transToErrorPDUString(httpPDUObj, ShapError.Class.Reject,ShapError.Code.RejectInvalidParameterDataType,null));
        }
        IShapService shapService = ClassLoad.InvokeClassMap.get(objectId);
        //利用propertyID获取方法名
       Result result =  shapService.invokeMethod(propertyId, value,ei);
        if(result.isSuccess()){
            httpPDUObj.setData(result.getData());
            return this.encodeComplexAck(httpPDUObj);
        }   else{
            httpPDUObj = ShapUtil.transToErrorPDUString(httpPDUObj,result.getErrorClass(),result.getErrorCode(),result.getData());
            return encodeError(httpPDUObj);
        }
    }

    @Override
    public String handleApdu(String constant) {
        if(constant==null || "".equals(constant.trim())){
            return "data not null!";
        }
        this.decode(constant);
        try {
            switch (httpPDUObj.getType()) {
               case ShapConstants.PduType.ConfirmedReq:
                    switch (httpPDUObj.getServiceType())
                    {
//                        case ShapConstants.ServiceType.ReadProperty:
////                            handleReadProperty(apdu);
////                            break;
//                        case ShapConstants.ServiceType.ReadPropertyMultiple:
////                            handleReadPropertyMultiple(apdu);
////                            break;
//                        case ShapConstants.ServiceType.WriteProperty:
////                            handleWriteProperty(apdu);
////                            break;
//                        case ShapConstants.ServiceType.WritePropertyMultiple:
////                            handleWritePropertyMultiple(apdu);
////                            break;
//                        case ShapConstants.ServiceType.ReadFile:
////                            handleReadFile(apdu);
////                            break;
//                        case ShapConstants.ServiceType.WriteFile:
////                            handleWriteFile(apdu);
////                            break;
                        case ShapConstants.ServiceType.InvokeMethod:
                            return this.handleInvokeMethod();
//                             handleInvokeMethod(apdu);
//                            break;
//                        case ShapConstants.ServiceType.ReceiveRequest:
//                            handleReceiveRequest(apdu);
//                            break;
//                        case ShapConstants.ServiceType.SendRequest:
//                            handleSendRequest(apdu);
//                            break;
//                        default:
//                            sendError(apdu.getContext(), apdu.getInvokeId(), -1, ShapError.Class.Reject, ShapError.Code.RejectUnrecognizedService);
//                            break;
//                    }
//                    break;
                case ShapConstants.PduType.SimpleAck:
//                    handleSimpleAck(apdu);
//                    break;
                case ShapConstants.PduType.ComplexAck:
//                    switch (apdu.getServiceType())
//                    {
//                        case ShapConstants.ServiceType.ReadProperty:
//                            handleReadPropertyAck(apdu);
//                            break;
//                        case ShapConstants.ServiceType.ReadPropertyMultiple:
//                            handleReadPropertyMultipleAck(apdu);
//                            break;
//                        case ShapConstants.ServiceType.WritePropertyMultiple:
//                            handleWritePropertyMultipleAck(apdu);
//                            break;
//                        case ShapConstants.ServiceType.ReadFile:
//                            handleReadFileAck(apdu);
//                            break;
//                        case ShapConstants.ServiceType.WriteFile:
//                            handleWriteFileAck(apdu);
//                            break;
//                        case ShapConstants.ServiceType.InvokeMethod:
//                            handleInvokeMethodAck(apdu);
//                            break;
//                        case ShapConstants.ServiceType.ReceiveRequest:
//                            handleReceiveRequestAck(apdu);
//                            break;
//                        case ShapConstants.ServiceType.SendRequest:
//                            handleSendRequestAck(apdu);
//                            break;
//                    }
//                    break;
                case ShapConstants.PduType.UnconfirmedReq:
//                    switch (apdu.getServiceType())
//                    {
//                        case ShapConstants.ServiceType.WhoIs:
//                            handleWhoIs(apdu);
//                            break;
//                        case ShapConstants.ServiceType.IAm:
//                            handleIAm(apdu);
//                            break;
//                    }
//                    break;
                case ShapConstants.PduType.Error:
                    break;
             }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
