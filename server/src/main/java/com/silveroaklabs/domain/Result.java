package com.silveroaklabs.domain;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
public class Result {
    private boolean success;
    private String msg;
    private int errorCode;
    private int errorClass;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(Object data) {

        List<Object> arrayList = new ArrayList<Object>();
        arrayList.add(data);
        this.data =(JSONArray.fromObject(arrayList)).toString();;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(int errorClass) {
        this.errorClass = errorClass;
    }
}
