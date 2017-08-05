package com.hesh.vo.user;

import java.io.Serializable;

/**
 * Created by zhaoji on 2015/7/18.
 */
public class PublicResponse<T> implements Serializable {
    private static final long serialVersionUID = 2334408592332198074L;
    //业务数据类--返回单个对象
    private T data;
    //是否成功
    private boolean success = true;

    //失败详细信息描述
    private String errorInfos;

    //错误编码
    private String errorCode;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorInfos() {
        return errorInfos;
    }

    public void setErrorInfos(String errorInfos) {
        this.errorInfos = errorInfos;
    }

    /**
     * 追加错误信息
     * @param errorInfo
     */
    public void appendErrorInfos(String errorInfo) {
        if(null == errorInfos) {
            errorInfos = new String();
        }
        errorInfos = errorInfos+","+errorInfo;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 设置错误编码和异常信息
     * @param errorCode
     * @param errorInfo
     */
    public void setErrorCodeAndErrorInfos(String errorCode, String errorInfo) {
        this.success = false;
        this.errorCode = errorCode;
        setErrorInfos(errorInfo);
    }

    @Override
    public String toString() {
        return "PublicResponse{" +
                "data=" + data +
                ", success=" + success +
                ", errorInfos=" + errorInfos +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
