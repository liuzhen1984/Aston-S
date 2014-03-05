package com.silveroaklabs.service.impl;

import com.silveroaklabs.constant.ShapConstants;
import com.silveroaklabs.constant.ShapError;
import com.silveroaklabs.dao.IDeviceDao;
import com.silveroaklabs.dao.IUserDao;
import com.silveroaklabs.domain.Result;
import com.silveroaklabs.domain.UserObj;
import com.silveroaklabs.init.SimulateRedis;
import com.silveroaklabs.service.IShapService;
import com.silveroaklabs.service.IUserService;
import com.silveroaklabs.util.SessionUtil;
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
@Service("userService")
public class UserServiceImpl implements IUserService,IShapService {
    private final String objectName = "User Account Service";
    private final int objectType = ShapConstants.ObjectType.UserAccountService;
    private final int objectIdentifier = ShapUtil.makeObjectIdentifier(1, objectType);



    @Autowired
    private IUserDao userDao;

    @Autowired
    private IDeviceDao deviceDao;

    @Override
    public Result userIdFromSessionKey(String sessionKey)
    {
        Result result = new Result();
        try {
            int userID = 0;
            //获取sessionkey
            if(SimulateRedis.UserDB.containsKey(sessionKey)){
                userID = SimulateRedis.UserDB.get(sessionKey);
            }
            result.setSuccess(true);
            result.setData(userID);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorCode(21);
        }
        return result;
    }
    @Override
    public Result createUser(String userName, String password)
    {
        Result result = new Result();
        try {
           UserObj userObj =new UserObj();
           userObj.setUsername(userName);
           userObj.setPassword(password);
           this.userDao.save(userObj);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorCode(22);
            result.setErrorClass(ShapError.Class.Services);
            result.setMsg(e.toString());
        }
        return result;
    }
    @Override
    public Result logonUser(String userName, String password)
    {
        Result result = new Result();
        try {
            if(this.userDao.auth(userName,password)){

                Map<String,Object> where = new HashMap<String,Object>();
                where.put("username",userName);
                where.put("password", StringUtil.md5(password.getBytes()));
                List<UserObj> users = this.userDao.find(where);
                //产生sessionkey;
                String sessionKey = SessionUtil.generateSessionId();
                //保存sessionkey到redis中
                SimulateRedis.UserDB.put(sessionKey,users.get(0).getId());
                //saveToRedis();
                result.setSuccess(true);
                result.setData(sessionKey);
                return result;
            } else{
                result.setSuccess(false);
                result.setErrorClass(ShapError.Class.Services);
                result.setErrorCode(23);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorCode(23);
            result.setMsg(e.toString());
            return result;
        }
    }
    @Override
    public Result logoutUser(String sessionKey)
    {
        Result result = new Result();
        try{
            //清楚redis中的sessionkey
            if(SimulateRedis.UserDB.containsKey(sessionKey)){
                SimulateRedis.UserDB.remove(sessionKey);
            }
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorCode(24);
        }
        return result;
    }

    @Override
    public Result getAccountInformation(String sessionKey)
    {
        Result result = new Result();
        //通过redis 中 sessionkey 获取 userId
        if(!SimulateRedis.UserDB.containsKey(sessionKey)){
            result.setSuccess(false);
            result.setErrorCode(ShapError.Code.UnknownObject);
            result.setErrorClass(ShapError.Class.Services);
            return result;
        }
        int userid = SimulateRedis.UserDB.get(sessionKey);
        UserObj userObj = this.userDao.findOne(userid);

        if(userObj!=null){
            result.setSuccess(true);
            result.setData(userObj);
        }  else{
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorCode(25);
        }
        return result;
    }

    @Override
    public Result setAccountInformation(String sessionKey, String data){
        Result result = new Result();
        //通过redis 中 sessionkey 获取 userId
        if(!SimulateRedis.UserDB.containsKey(sessionKey)){
            result.setSuccess(false);
            result.setErrorCode(ShapError.Code.UnknownObject);
            result.setErrorClass(ShapError.Class.Services);
            return result;
        }
        int userid = SimulateRedis.UserDB.get(sessionKey);

        //修改数据值
        Map<String,Object> value = new HashMap<String, Object>();
        value.put("email",data);
        this.userDao.update(userid,value);

        result.setSuccess(true);
        result.setData(this.userDao.findOne(userid));
        return result;
    }

    @Override
    public Result changeUserPassword(String sessionKey, String oldPassword, String newPassword)
    {
        Result result = new Result();
        if(!SimulateRedis.UserDB.containsKey(sessionKey)){
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorCode(23);
            return result;
        }
        //判断改密码是否正确
        Map<String,Object>  whereMap=new HashMap<String, Object>();
        int userid = SimulateRedis.UserDB.get(sessionKey);
        whereMap.put("id",userid);
        whereMap.put("password",StringUtil.md5(oldPassword.getBytes()));
        if(this.userDao.find(whereMap).size()<1){
            result.setSuccess(false);
            result.setErrorClass(ShapError.Class.Services);
            result.setErrorCode(23);
            return result;
        }
        this.userDao.updatePasswd(userid,oldPassword,newPassword);
        SimulateRedis.UserDB.remove(sessionKey);
        result.setSuccess(true);
        return result;
    }
    @Override
    public Result bindDevice(String sessionKey, String deviceKey)
    {
        Result result = new Result();
        //通过redis 中 sessionkey 获取 userId
        if(SimulateRedis.UserDB.containsKey(sessionKey) && SimulateRedis.BindDeviceDB.containsKey(deviceKey)){
             //保存设备信息

            String deviceSn = SimulateRedis.BindDeviceDB.get(deviceKey);
            int userId = SimulateRedis.UserDB.get(sessionKey);
            try{
                 this.deviceDao.bind(userId,0,deviceSn);
                 SimulateRedis.BindDeviceDB.remove(deviceKey);

            }catch (Exception ex){
                 result.setSuccess(false);
                result.setErrorCode(ShapError.Code.ServiceException);
                result.setErrorClass(ShapError.Class.Services);
                result.setMsg(ex.toString());
            }
        }
        result.setSuccess(true);
        return result;
    }
    @Override
    public Result unbindDevice(String sessionKey, String deviceKey)
    {
        Result result = new Result();
        result.setSuccess(false);
        return result;
    }

    @Override
    public int getObjectType(){
        return this.objectType;
    }

    @Override
    public int getObjectIdentifier(){
        return this.objectIdentifier;
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
            case ShapConstants.PropertyId.Create_User:
                return this.createUser(args.optString(0), args.optString(1));
            case ShapConstants.PropertyId.Logon_User:
                return logonUser(args.optString(0), args.optString(1));
            case ShapConstants.PropertyId.Logout_User:
                return logoutUser(args.optString(0));
            case ShapConstants.PropertyId.Get_Account_Information:
                return getAccountInformation(args.optString(0));
            case ShapConstants.PropertyId.Set_Account_Information:
                return setAccountInformation(args.optString(0), args.optString(1));
            case ShapConstants.PropertyId.Change_User_Password:
                return changeUserPassword(args.optString(0), args.optString(1), args.optString(2));
            case ShapConstants.PropertyId.Bind_Device:
                return bindDevice(args.optString(0), args.optString(1));
            case ShapConstants.PropertyId.Unbind_Device:
                return unbindDevice(args.optString(0), args.optString(1));
            default:
                 result.setSuccess(false);
                result.setErrorClass(ShapError.Class.Reject);
                result.setErrorCode(ShapError.Code.RejectInvalidParameterDataType);
                return result;
        }
    }
}
