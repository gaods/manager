package com.hesh.vo.user;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-8-4
 * Time: 上午10:09
 * To change this template use File | Settings | File Templates.
 */
public class RedisKey {
    private static final String STOKEN= "HS";
    public final static String HS_XC_TOKEN =STOKEN + ".XC_";
    public final static String HS_XC_IP =STOKEN + ".XC.IP_";
    public final static String HS_USE =STOKEN + ".PHONE_";
    public final static String HS_CUSTOMER =STOKEN + ".CID_";
}
