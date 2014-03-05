package com.silveroaklabs.dao.impl;

import com.silveroaklabs.dao.AbstractBaseDao;
import com.silveroaklabs.dao.IUserDao;
import com.silveroaklabs.domain.UserObj;
import com.silveroaklabs.rowmap.UserRowMapper;
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
 * Date: 14-2-24
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractBaseDao implements IUserDao {
    private final String tbName="s_user";
    @Override
    public int save(UserObj userObj) throws Exception {
        try {
            userObj.setPassword(StringUtil.md5(userObj.getPassword().getBytes()));
            userObj.setCreateTime(new Date().getTime());
            Map<String,Object> userMap = DBUtil.userObjToMap(userObj);
            return this.save(this.tbName,userMap);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }
    @Override
    public boolean updatePasswd(int userid, String password, String newPassword){
        //判断改密码是否正确
        Map<String,Object>  whereMap=new HashMap<String, Object>();
        whereMap.put("id",userid);
        whereMap.put("password",StringUtil.md5(password.getBytes()));
        HashMap<String,Object> valueMap = new HashMap<String,Object>();
        valueMap.put("password", StringUtil.md5(newPassword.getBytes()));
        this.updateAttr(this.tbName,valueMap,whereMap);
       return true;
    }
    @Override
    public boolean update(int userid, Map<String,Object> value){
        //判断改密码是否正确
        Map<String,Object>  whereMap=new HashMap<String, Object>();
        whereMap.put("id",userid);

        if(value.containsKey("password")){
            value.remove("password");
        }

        this.updateAttr(this.tbName,value,whereMap);
        return true;
    }

    @Override
    public boolean auth(String username, String password){
        Map<String,Object>  whereMap=new HashMap<String, Object>();
        whereMap.put("username",username);
        whereMap.put("password",StringUtil.md5(password.getBytes()));
        List<UserObj> users = this.find(whereMap);
        if(users.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public UserObj findOne(int userID){
        Map<String,Object>  whereMap=new HashMap<String, Object>();
        whereMap.put("id",userID);
        List<UserObj> users = this.find(whereMap);
        if(users.size()>0){
            return users.get(0);
        }
        return null;
    }
    @Override
    public List<UserObj> find(Map<String, Object> where){
        StringBuffer sqlStr = new StringBuffer("select * from "+this.tbName+" where ");

        sqlStr.append(DBUtil.mapToWhere(where));

        return this.jdbcTemplate.query(sqlStr.toString(),new UserRowMapper());
    }
}
