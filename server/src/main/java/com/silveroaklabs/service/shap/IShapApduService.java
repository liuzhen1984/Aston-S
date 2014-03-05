package com.silveroaklabs.service.shap;

import com.silveroaklabs.domain.apdu.ShapPDUObj;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-2
 * Time: 下午5:17
 * To change this template use File | Settings | File Templates.
 */
public interface IShapApduService {
    final int vde=0;
    Object encodeConfirmedReq(ShapPDUObj confirmeReq) throws Exception;

    Object encodeUnconfirmedReq(ShapPDUObj unConfirmeReq) throws Exception;

    Object encodeSimpleAck(ShapPDUObj simpleAckPDUObj) throws Exception;

    Object encodeComplexAck(ShapPDUObj complexAck);

    Object encodeError(ShapPDUObj errorPDU);

    int getVDE();
    ShapPDUObj decode(String data);

    String handleApdu(String constant);
}
