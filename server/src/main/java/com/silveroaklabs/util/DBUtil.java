package com.silveroaklabs.util;

import com.silveroaklabs.domain.DeviceObj;
import com.silveroaklabs.domain.UserObj;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
public class DBUtil {
    public static String mapToWhere(Map<String,Object> where){
        StringBuilder sqlWhereSbf = new StringBuilder();
        for(Map.Entry<String, Object> entry:where.entrySet()){
            String param = entry.getKey();
            if(entry.getValue()==null){
                continue;
            }
            if("".equals(sqlWhereSbf.toString())){
                sqlWhereSbf.append(param+"=\""+entry.getValue()+"\" ");
            } else{
                sqlWhereSbf.append(" and "+param+"=\""+entry.getValue()+"\" ");
            }
        }
        return sqlWhereSbf.toString();
    }

    public static Map<String,Object> userObjToMap(UserObj userObj){
        if(userObj==null){
            return null;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",userObj.getId());
        map.put("username",userObj.getUsername());
        map.put("password",userObj.getPassword());
        map.put("email",userObj.getEmail());
        map.put("create_time",userObj.getCreateTime());

        return map;
    }
    public static Map<String,Object> deviceObjToMap(DeviceObj deviceObj){
        if(deviceObj==null){
            return null;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("sn",deviceObj.getSn());
        map.put("firmware_version",deviceObj.getFirmwareVersion());
        map.put("mac",deviceObj.getMac());
        map.put("protocol_version",deviceObj.getProtocolVersion());
        map.put("user",deviceObj.getUserID());
        return map;
    }
}
