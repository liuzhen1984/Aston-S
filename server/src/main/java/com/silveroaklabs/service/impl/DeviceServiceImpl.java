package com.silveroaklabs.service.impl;

import com.silveroaklabs.constant.ShapConstants;
import com.silveroaklabs.constant.ShapError;
import com.silveroaklabs.dao.IDeviceDao;
import com.silveroaklabs.domain.DeviceObj;
import com.silveroaklabs.domain.Result;
import com.silveroaklabs.init.SimulateRedis;
import com.silveroaklabs.service.IDeviceService;
import com.silveroaklabs.service.IShapService;
import com.silveroaklabs.util.ShapUtil;
import com.silveroaklabs.util.StringUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午7:03
 * To change this template use File | Settings | File Templates.
 */
@Service("deviceService")
public class DeviceServiceImpl implements IDeviceService,IShapService {
    private final String objectName = "User Account Service";
    private final int objectType = ShapConstants.ObjectType.DeviceService;
    private final int objectIdentifier = ShapUtil.makeObjectIdentifier(1,objectType);

    @Autowired
    private IDeviceDao deviceDao;

    @Override
    public String getDevices(){
        return null;
    }

    @Override
    public Result getDevices(String sessionKey){
        Result result = new Result();
        //通过redis获取sessionKey，用户 得到设备id
        int userID=0;
        Map<String,Object> where = new HashMap<String, Object>();
        where.put("user",userID);
        List<DeviceObj> deviceObjs = this.deviceDao.find(where);
        result.setSuccess(true);
        result.setData(deviceObjs);
        return result;
    }

    @Override
    public Result getDevice(String sn){
        return null;
    }
    @Override
    public Result getDevicesFromUser(String sessionKey){
        Result result = new Result();
        if(!SimulateRedis.UserDB.containsKey(sessionKey)){
            result.setSuccess(false);
            result.setErrorCode(ShapError.Code.UnknownProperty);
            result.setErrorClass(ShapError.Class.Services);
            return result;
        }
        int userId = SimulateRedis.UserDB.get(sessionKey);
        Map<String,Object> where = new HashMap<String, Object>();
        where.put("user_id",userId);
        List<DeviceObj> deviceObjs = this.deviceDao.find(where);
        result.setSuccess(true);
        result.setData(deviceObjs);
        return result;
    }

    @Override
    public Result register(String sn,String mac){
        Result result = new Result();

        DeviceObj deviceObj = this.deviceDao.findOne(sn);
        if(deviceObj==null){
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorClass(ShapError.Code.UnknownDevice);
        }  else{
            if(deviceObj.getRegister()==1){
                result.setSuccess(true);
                result.setData(deviceObj.getDeviceKey());
                return result;
            }
        }

        try {
            String deviceKey = this.deviceDao.register(0,sn);
            SimulateRedis.DeviceDB.put(deviceKey,sn);
            result.setSuccess(true);
            result.setData(deviceKey);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorClass(ShapError.Code.InvokeAccessDenied);
            result.setMsg(e.toString());
        }
        return result;
    }

    @Override
    public Result enableBinding(String deviceKey){

        Result result = new Result();
        if(!SimulateRedis.DeviceDB.containsKey(deviceKey)){
            result.setSuccess(false);
            result.setErrorCode(ShapError.Code.UnknownProperty);
            result.setErrorClass(ShapError.Class.Services);
            return result;
        }
        String sn = SimulateRedis.DeviceDB.get(deviceKey);
        //产生一个8位的随机码
        String bindNum = StringUtil.rangNum(8);
        //把sn号和产生的随机码放入redis中
        SimulateRedis.BindDeviceDB.put(bindNum, sn);
        result.setSuccess(true);
        result.setData(bindNum);
        return result;
    }

    public Result storeData(String deviceKey,String data){
        Result result = new Result();
        if(SimulateRedis.DeviceDB.containsKey(deviceKey)){
            result.setSuccess(true);
        //save to mongodb
        }else{
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorCode(ShapError.Code.UnknownDevice);
        }
        return result;
    }

    @Override
    public Result invokeMethod(int pid, Object value, int ei) {
        Result result = new Result();
        if (!(value instanceof JSONArray))  {
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Reject);
            result.setErrorCode(ShapError.Code.RejectInvalidParameterDataType);
            return result;
        }
        JSONArray args = (JSONArray)value;
        switch (pid)
        {
            case ShapConstants.PropertyId.Register_Device:
                return this.register(args.optString(0),args.optString(0));
            case ShapConstants.PropertyId.Enable_Binding:
                return this.enableBinding(args.optString(0));
            case ShapConstants.PropertyId.Find_Device_User:
                return this.getDevicesFromUser(args.optString(0));
            case ShapConstants.PropertyId.Store_Data:
                return storeData(args.optString(0), args.optString(1));
//            case ShapConstants.PropertyId.Send_Notification:
//                return sendNotification(args.optString(0), args.optString(1));
            default:
                result.setSuccess(false);
                result.setErrorClass(ShapError.Class.Reject);
                result.setErrorCode(ShapError.Code.RejectInvalidParameterDataType);
                return result;
        }
    }

    @Override
    public int getObjectType() {
        return this.objectType;
    }

    @Override
    public int getObjectIdentifier() {
        return this.objectIdentifier;
    }
}
