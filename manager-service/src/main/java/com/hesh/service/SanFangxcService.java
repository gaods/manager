package com.hesh.service;

import com.hesh.vo.user.PublicResponse;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-7-30
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public interface SanFangxcService {
    public PublicResponse  getZcPhonePassWord(Map<String,Object>  map);

    public PublicResponse getYzNumber(Map<String, Object> map);

    public PublicResponse<String> getXingChenToken();

    public PublicResponse getSfNumber(Map<String, Object> map);

    public PublicResponse getYgNumber(Map<String, Object> map);

    public   boolean getExitSanFangLogin();
}
