package com.silveroaklabs.dao.impl;

import com.silveroaklabs.constant.AttributeConstants;
import com.silveroaklabs.dao.AbstractBaseDao;
import com.silveroaklabs.dao.IDeviceDao;
import com.silveroaklabs.domain.DeviceObj;
import com.silveroaklabs.rowmap.DeviceRowMapper;
import com.silveroaklabs.util.DBUtil;
import com.silveroaklabs.util.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
@Repository("deviceDao")
public class DeviceDaoImpl extends AbstractBaseDao implements IDeviceDao {
    private final String tbName = "s_device";

    @Override
    public int save(DeviceObj deviceObj)throws Exception{
        try {
            Map<String,Object> deviceMap = DBUtil.deviceObjToMap(deviceObj);
            deviceMap.put("is_register",0);

            return this.save(this.tbName,deviceMap);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @Override
    public DeviceObj findOne(String sn) {
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("sn", sn);
        List<DeviceObj> deviceObjs = this.find(where);

        if (deviceObjs.size() < 1) {
            return null;
        }
        return deviceObjs.get(0);
    }

    @Override
    public List<DeviceObj> find(Map<String, Object> where) {
        StringBuffer sqlStr = new StringBuffer("select * from " + this.tbName);
        if (where.keySet().size() > 0) {
            sqlStr.append(" where ");
            sqlStr.append(DBUtil.mapToWhere(where));
        }
        return this.jdbcTemplate.query(sqlStr.toString(), new DeviceRowMapper());
    }

    @Override
    public boolean bind(int userid, int houseid, String sn){
        //判断改密码是否正确
        Map<String,Object>  whereMap=new HashMap<String, Object>();
        whereMap.put("sn",sn);
        HashMap<String,Object> valueMap = new HashMap<String,Object>();
        valueMap.put("user_id",userid);
        valueMap.put("house_id",houseid);
        valueMap.put("is_bind", AttributeConstants.IS_BIND.YES );
        valueMap.put("bind_time",new Date().getTime());
        this.updateAttr(this.tbName,valueMap,whereMap);
        return true;
    }
    @Override
    public String register(int houseid, String sn){
        Date cd = new Date();
        //判断改密码是否正确
        Map<String,Object>  whereMap=new HashMap<String, Object>();
        whereMap.put("sn",sn);
        HashMap<String,Object> valueMap = new HashMap<String,Object>();
        String deviceKey =  StringUtil.md5((sn + cd.getTime()).getBytes());
        valueMap.put("device_key",deviceKey);
        valueMap.put("house_id",houseid);
        valueMap.put("is_register", AttributeConstants.IS_REGISTER.YES );
        valueMap.put("register_time",cd.getTime());
        this.updateAttr(this.tbName,valueMap,whereMap);
        return deviceKey;
    }


}
