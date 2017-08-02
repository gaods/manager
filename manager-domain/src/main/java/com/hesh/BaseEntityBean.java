//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesh;

import java.io.Serializable;
import java.util.Date;

public class BaseEntityBean implements Serializable {
    private static final long serialVersionUID = -7793739903799136331L;
    private Integer sysVersion;
    private Date createTime;
    private Date modifyTime;
    private String creator;
    private String modifier;
    private Integer dr;


    public BaseEntityBean() {
    }

    public Integer getSysVersion() {
        return this.sysVersion;
    }

    public void setSysVersion(Integer sysVersion) {
        this.sysVersion = sysVersion;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getDr() {
        return this.dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }


    public BaseEntityBean init() {
        this.creator = "";
        this.modifier = "";
        //this.dr = YNEnum.Y.getCode();
        return this;
    }
}
