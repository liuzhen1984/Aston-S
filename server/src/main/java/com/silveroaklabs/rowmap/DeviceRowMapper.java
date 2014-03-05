package com.silveroaklabs.rowmap;

import com.silveroaklabs.domain.DeviceObj;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午12:17
 * To change this template use File | Settings | File Templates.
 */
public class DeviceRowMapper implements RowMapper<DeviceObj> {
        @Override
    public DeviceObj mapRow(ResultSet rs, int rowNum) throws SQLException {
            DeviceObj deviceObj = new DeviceObj();
            deviceObj.setFirmwareVersion(rs.getString("firmware_version"));
            deviceObj.setProtocolVersion( rs.getInt("protocol_version"));
            deviceObj.setMac(rs.getString("mac"));
            deviceObj.setRegister(rs.getInt("is_register"));
            deviceObj.setCreateTime(rs.getLong("create_time"));
            deviceObj.setRegisterTime(rs.getLong("register_time"));
            deviceObj.setUserID(rs.getInt("user_id"));
            deviceObj.setBind(rs.getInt("is_bind"));
            deviceObj.setBindTime(rs.getLong("bind_time"));
            deviceObj.setDeviceKey(rs.getString("device_key"));
            deviceObj.setSn(rs.getString("sn"));
        return deviceObj;
    }
}
