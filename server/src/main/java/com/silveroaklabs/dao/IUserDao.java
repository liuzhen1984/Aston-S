package com.silveroaklabs.dao;

import com.silveroaklabs.domain.UserObj;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午12:34
 * To change this template use File | Settings | File Templates.
 */
public interface IUserDao {
    int save(UserObj userObj) throws Exception;

    boolean updatePasswd(int userid, String password, String newPassword);

    boolean auth(String username, String password);

    UserObj findOne(int userID);

    List<UserObj> find(Map<String, Object> where);

    boolean update(int userid, Map<String, Object> value);
}
