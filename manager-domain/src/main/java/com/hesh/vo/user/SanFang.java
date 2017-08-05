package com.hesh.vo.user;


import com.hesh.BaseEntityBean;

import java.util.Date;

/**
 *
 * pa_customer
 *
 **/
@SuppressWarnings("serial")
public class SanFang extends BaseEntityBean {

    private static final long serialVersionUID = 1L;
    /**自增ID**/
    private Integer id;
    private String sfCode;
    private String sfPassword;
    private String sfIp;
    private String state;

    /**创建人**/
    private String creater;
    /**创建时间**/
    private java.util.Date creatTime;
    /**修改人**/
    private String modifiter;
    private java.util.Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSfCode() {
        return sfCode;
    }

    public void setSfCode(String sfCode) {
        this.sfCode = sfCode;
    }

    public String getSfPassword() {
        return sfPassword;
    }

    public void setSfPassword(String sfPassword) {
        this.sfPassword = sfPassword;
    }

    public String getSfIp() {
        return sfIp;
    }

    public void setSfIp(String sfIp) {
        this.sfIp = sfIp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getModifiter() {
        return modifiter;
    }

    public void setModifiter(String modifiter) {
        this.modifiter = modifiter;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
