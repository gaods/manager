package com.hesh.vo.user;


import java.io.Serializable;

/**
 *
 * pa_customer
 *
 **/
@SuppressWarnings("serial")
public class Customer  implements Serializable{

    private static final long serialVersionUID = 1L;
    /**自增ID**/
    private Integer id;

    /**客户经理工号**/
    private String pnCode;

    /**注册数量**/
    private Integer zcCount;

    /**标识（0未开始，1完成，2删除，3未完成）**/
    private String pastate;

    /**状态**/
    private String state;

    /**创建人**/
    private String creater;

    /**创建时间**/
    private java.util.Date creatTime;

    /**修改人**/
    private String modifiter;

    /**修改时间**/
    private java.util.Date modifyTime;



    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public void setPnCode(String pnCode){
        this.pnCode = pnCode;
    }

    public String getPnCode(){
        return this.pnCode;
    }

    public void setZcCount(Integer zcCount){
        this.zcCount = zcCount;
    }

    public Integer getZcCount(){
        return this.zcCount;
    }

    public void setPastate(String pastate){
        this.pastate = pastate;
    }

    public String getPastate(){
        return this.pastate;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    public void setCreater(String creater){
        this.creater = creater;
    }

    public String getCreater(){
        return this.creater;
    }

    public void setCreatTime(java.util.Date creatTime){
        this.creatTime = creatTime;
    }

    public java.util.Date getCreatTime(){
        return this.creatTime;
    }

    public void setModifiter(String modifiter){
        this.modifiter = modifiter;
    }

    public String getModifiter(){
        return this.modifiter;
    }

    public void setModifyTime(java.util.Date modifyTime){
        this.modifyTime = modifyTime;
    }

    public java.util.Date getModifyTime(){
        return this.modifyTime;
    }

}
