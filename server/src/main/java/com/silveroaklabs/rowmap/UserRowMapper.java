package com.silveroaklabs.rowmap;

import com.silveroaklabs.domain.UserObj;
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
public class UserRowMapper implements RowMapper<UserObj> {
     @Override
    public UserObj mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserObj userObj = new UserObj();
            userObj.setId(rs.getInt("id"));
            userObj.setUsername(rs.getString("username"));
            userObj.setEmail(rs.getString("email"));
            userObj.setCreateTime(rs.getLong("create_time"));
        return userObj;
    }
}
