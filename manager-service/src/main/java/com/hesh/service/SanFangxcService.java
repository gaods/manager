package com.hesh.service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-7-30
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public interface SanFangxcService {
    public Map<String,Object>  getZcPhonePassWord(Map<String,Object>  map);

    public Map<String, Object> getYzNumber(Map<String, Object> map);

    public String getXingChenToken(Map<String,Object> map);
    public Map<String, Object> getSfNumber(Map<String, Object> map);
}
