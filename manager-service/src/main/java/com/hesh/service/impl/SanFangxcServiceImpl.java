package com.hesh.service.impl;

import com.hesh.service.SanFangxcService;
//import com.hesheng.utils.GetRandomNumber;
import com.hesh.utils.RedisClient;
import com.hesheng.utils.GetRandomNumber;
import com.hesheng.utils.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-7-30
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
@Service("sanFangxcService")
public class SanFangxcServiceImpl implements SanFangxcService {
    private static final Log logger = LogFactory.getLog(SanFangxcServiceImpl.class);

    private final int sl=1;

    @Resource
    RedisClient redisClient;


    /**
     * 获取号码密码
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getZcPhonePassWord(Map<String, Object> map) {
        if(null!=map && null!=map.get("opFlag")){
            Map<String, Object> ppMap = new HashMap<String, Object>();
            String phoneNumber = "";
            String xmid = "";
            String token = "";
            String url = "http://www.xingchenma.com:9180/service.asmx/GetHMStr?token=132A49069398917D4C666B1BA77242C8&xmid=200&sl=1&lx=0&a1=&a2=&pk=";
            String paPhone  = HttpClientUtil.httpGetRequest(url);
            if(null!=paPhone&&!("").equals(paPhone)){
                if(paPhone.length()>3){
                    String code = paPhone.substring(0,2);
                    if(code.equals("hm")){
                        String phoneNumbers = paPhone.replace("hm=","");
                        String[] paPhones = phoneNumbers.split(",");
                        phoneNumber=paPhones[0];
                        logger.info(paPhones);
                    }
                    if(code.equals("id")){
                        String xid = paPhone.replaceAll("id=","");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        logger.info(xid);
                    }
                }
            }
            String paPassWord = GetRandomNumber.generateLowerString(8);
            ppMap.put("phoneNumber",phoneNumber);
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
        try{
            if(null!=map && null!=map.get("xcUserName") && null!=map.get("xcPassWord")){
                String user = (String)map.get("xcUserName")  ;
                String passWord = (String)map.get("xcPassWord")  ;
                String url ="http://www.xingchenma.com:9180/service.asmx/UserLoginStr?name="+user+"&psw="+passWord;
                String token =   HttpClientUtil.httpGetRequest(url) ;
                //三方接口返回是否为空校验
                if(null!=token && !("").equals(token)){
                    if(token.length()>20){
                        //TODO 存储token信息在缓存中，方便下次直接使用每次任务结束清理缓存信息
                        return token;
                    }
                }else {
                    logger.info("调用接口超时异常"+token);
                    return null;
                }
            }
        } catch (Exception ex){
              logger.error("getXingChenToken"+ex);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
