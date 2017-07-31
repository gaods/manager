package com.hesh.web.vo;


/**
 * Created by caolin on 2016/9/13.
 */
public class ResultObjectVo<T> {
    //0失败1成功
    private int status;

    //单个对象
    private T data = null;

    //成功或失败的消息
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultObjectVo(){}
    public ResultObjectVo(int status){
        if(status==0){
            this.msg="操作失败！";
        }else{
            this.msg="操作成功！";
        }
        this.status=status;
    }



}
