package com.hesh.web.vo;

import java.io.Serializable;

/**
 * Created by gaods on 2017/7/24.
 */
public class MsgVo implements Serializable{

    public String issuccess;

    public String msg;

    private String data;

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
