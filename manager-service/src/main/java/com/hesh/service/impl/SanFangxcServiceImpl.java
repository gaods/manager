package com.hesh.service.impl;

import com.hesh.service.SanFangxcService;
//import com.hesheng.utils.GetRandomNumber;
import com.hesheng.utils.GetRandomNumber;
import com.hesheng.utils.HttpClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-7-30
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
@Service("sanFangxcService")
public class SanFangxcServiceImpl implements SanFangxcService {

    /**
     * 获取号码密码
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getZcPhonePassWord(Map<String, Object> map) {
        if(null!=map && null!=map.get("opFlag")){
            Map<String, Object> ppMap = new HashMap<String, Object>();
            String xmid = "";
            String token = "";
            String sl = "";
            String url = "http://www.xingchenma.com:9180/service.asmx/GetHMStr?token=132A49069398917D4C666B1BA77242C8&xmid=200&sl=1&lx=0&a1=&a2=&pk=";
            String paPhone  = HttpClientUtil.httpGetRequest(url);
            String paPassWord = GetRandomNumber.generateLowerString(8);
            ppMap.put("phoneNumber",paPhone);
            ppMap.put("passWord",paPassWord);
            return ppMap;

        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    /**
     * 获取验证码
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getYzNumber(Map<String, Object> map) {
        if(null!=map && null!=map.get("hm")){
            Map<String, Object> ppMap = new HashMap<String, Object>();
            String xmid = "";
            String token = "";
            String hm = "";
            String url = "http://www.xingchenma.com:9180/service.asmx/GetYzmStr?token="+token+"&hm="+hm+"&xmid="+xmid;
            String yzNum = HttpClientUtil.httpGetRequest(url);
            if(null!=yzNum&&!("").equals(yzNum)){
                 if(("1").equals(yzNum)){

                 }
            }
          //  ppMap.put("passWord",paPassWord);
            return ppMap;

        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 释放号码
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getSfNumber(Map<String, Object> map) {
        if(null!=map && null!=map.get("hm")){
            Map<String, Object> ppMap = new HashMap<String, Object>();
            String xmid = "";
            String token = "";
            String hm = "";
            String url = "http://www.xingchenma.com:9180/service.asmx/sfHmStr?token="+token+"&hm="+hm;
            String yzNum = HttpClientUtil.httpGetRequest(url);
            if(null!=yzNum&&!("").equals(yzNum)){
                 if(("1").equals(yzNum)){

                 }
            }
          //  ppMap.put("passWord",paPassWord);
            return ppMap;

        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 登录验证
     * @param map
     * @return
     */
    @Override
    public String getXingChenToken(Map<String, Object> map) {
        if(null!=map && null!=map.get("xcUserName") && null!=map.get("xcPassWord")){
            String user = (String)map.get("xcUserName")  ;
            String passWord = (String)map.get("xcPassWord")  ;
            String url ="http://www.xingchenma.com:9180/service.asmx/UserLoginStr?name="+user+"&psw="+passWord;
            String result =   HttpClientUtil.httpGetRequest(url) ;

            if(StringUtils.isEmpty(result)){

            }


            return result;
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
