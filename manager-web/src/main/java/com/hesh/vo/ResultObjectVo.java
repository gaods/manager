package com.hesh.vo;


/**
 * Created by caolin on 2016/9/13.
 */
public class ResultObjectVo<T> {
    //0失败1成功
    private String status=ResponseCode.success;

    //单个对象
    private T data = null;

    //成功或失败的消息
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
    public ResultObjectVo(String status){
        if(ResponseCode.success.equals(status)){
            this.msg="操作失败！";
        }else{
            this.msg="操作成功！";
        }
        this.status=status;
    }



}
