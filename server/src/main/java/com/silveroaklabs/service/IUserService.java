package com.silveroaklabs.service;

import com.silveroaklabs.domain.Result;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-3
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService {


    Result userIdFromSessionKey(String sessionKey);

    Result unbindDevice(String sessionKey, String deviceKey);

    Result bindDevice(String sessionKey, String deviceKey);

    Result changeUserPassword(String sessionKey, String oldPassword, String newPassword);

    Result getAccountInformation(String sessionKey);

    Result logoutUser(String sessionKey);

    Result logonUser(String userName, String password);

    Result createUser(String userName, String password);

    Result setAccountInformation(String sessionKey, String data);
}
