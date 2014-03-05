package com.silveroaklabs.util;

//import android.util.Log;

import com.silveroaklabs.constant.ShapConstants;
import com.silveroaklabs.domain.apdu.ShapPDUObj;

public final class ShapUtil {
    public static void writeLog(Object msg)
    {
//		Log.i("shaplog", msg.toString());
        System.out.println(msg);
    }
    public static int unsignedByte(byte b)
    {
        return b&0xff;
    }
    public static int makeErrorCode(int errorClass, int errorCode) {
        return errorClass|(errorCode&0xffff);
    }
    public static byte[] encodeIpFrame(byte[] apdu) {
        byte [] frame = new byte[apdu.length+4];

        frame[0] = (byte)0; // Type
        frame[1] = (byte)0; // Function


        // Data Length, MSB first
        frame[2] = (byte)((apdu.length & 0xff00)>>8);
        frame[3] = (byte)(apdu.length & 0xff);

        // PDU
        System.arraycopy(apdu, 0, frame, 4, apdu.length);
        return frame;
    }
    public static byte[] encodePtpFrame(byte[] apdu) {
        byte [] frame = new byte[apdu.length+8];
        // Preamble
        frame[0] = (byte)0xAA;
        frame[1] = (byte)0xFF;

        // Frame Type
        frame[2] = 0;

        // Data Length, MSB first
        frame[3] = (byte)((apdu.length & 0xff00)>>8);
        frame[4] = (byte)(apdu.length & 0xff);

        // Header CRC
        frame[5] = CRC8.updateBlock(frame, 2, 3, (byte)0xff);

        // PDU
        System.arraycopy(apdu, 0, frame, 6, apdu.length);

        // Data CRC
        short crc16 = CRC16.updateBlock(apdu, apdu.length, (short)0xffff);

        // LSB first
        frame[frame.length-2] = (byte)(crc16 & 0xff);
        frame[frame.length-1] = (byte)(crc16 >> 8);
        return frame;
    }


	public static int makeObjectIdentifier(int flat,int type){
        return flat | type<<24 ;
    }

    public static ShapPDUObj transToErrorPDUString(ShapPDUObj shapPDUObj,int errorClass,int errorCode,Object data){
        shapPDUObj.setType(ShapConstants.PduType.Error);
        shapPDUObj.setErrorClass(errorClass);
        shapPDUObj.setErrorCode(errorCode);
        shapPDUObj.setData(data);
        return shapPDUObj;
    }
}
