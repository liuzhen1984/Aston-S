package com.silveroaklabs.service;

import com.silveroaklabs.domain.Result;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-3-4
 * Time: 上午12:01
 * To change this template use File | Settings | File Templates.
 */
public interface IShapService {
    public Result invokeMethod(int pid,Object value,int ei);
    int getObjectType();
    int getObjectIdentifier();
}
