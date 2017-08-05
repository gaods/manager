package com.hesh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hesh.dao.SanFangMapper;
import com.hesh.service.CustomerService;
import com.hesh.service.SanFangxcService;
import com.hesh.utils.RedisClient;
import com.hesh.vo.user.*;
import com.hesheng.utils.GetRandomNumber;
import com.hesheng.utils.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//import com.hesheng.utils.GetRandomNumber;

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
    @Resource
    SanFangMapper sanFangMapper;
    @Resource
    CustomerService customerService;
    /**
     * 获取号码密码
     * @param map
     * @return
     */
    @Override
    public PublicResponse getZcPhonePassWord(Map<String, Object> map) {
        PublicResponse publicResponse = new PublicResponse();
        if(null!=map && null!=map.get("opFlag")){
            Map<String, Object> ppMap = new HashMap<String, Object>();
            String phoneNumber = "";
            String xmid = this.getXmId(map.get("opFlag"));
            String token = redisClient.get(RedisKey.HS_XC_TOKEN);
            String sfIp=redisClient.get(RedisKey.HS_XC_IP); //数据库里查询缓存获得
            String url =   "http://"+sfIp+":9180/service.asmx/GetHMStr?token="+token+"&xmid="+xmid+"&sl=1&lx=0&a1=&a2=&pk=";
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
                            TimeUnit.SECONDS.sleep(3);    //TODO 等待十秒重新查询三方接口获取ID对应的电话号码
                            String taskUrl =   "http://"+sfIp+":9180/service.asmx/GetTaskStr?token="+token+"&id="+xid;
                            paPhone  = HttpClientUtil.httpGetRequest(taskUrl);
                            if(null!=paPhone&&!("").equals(paPhone)){
                                if(paPhone.length()>10){
                                    String[] paPhones = paPhone.split(",");
                                    phoneNumber=paPhones[0];
                                } else {
                                   this.getZcPhonePassWord(map);
                                }
                            }
                        } catch (InterruptedException e) {
                            publicResponse.setSuccess(false);
                            publicResponse.setData(paPhone);
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        logger.info("getZp得到的ID"+xid);
                    }
                }else{
                       if(("0").equals(paPhone)){
                              this.getXingChenToken();
                       }else {
                           publicResponse.setSuccess(false);
                           publicResponse.setData(paPhone);
                       }
                }
            }else{
                publicResponse.setSuccess(false);
                publicResponse.setData(paPhone);
            }
            if(null!=phoneNumber&&!("").equals(phoneNumber)){
                String paPassWord = GetRandomNumber.generateLowerString(8);
                ppMap.put("phoneNumber",phoneNumber);
                ppMap.put("passWord",paPassWord);
                redisClient.set(RedisKey.HS_USE+phoneNumber, JSONObject.toJSONString(ppMap));
                publicResponse.setSuccess(true);
                publicResponse.setData(ppMap);
            }
        }
        return publicResponse;
    }
    /**
     * 获取验证码
     * @param map
     * @return
     */
    @Override
    public PublicResponse getYzNumber(Map<String, Object> map) {
        PublicResponse publicResponse = new PublicResponse();
        if(null!=map && null!=map.get("phoneNumber")){
            Map<String, Object> ppMap = new HashMap<String, Object>();
            String yzmCode ="";
            String yzNum="";
            String xmid = this.getXmId(map.get("opFlag"));
            String token = redisClient.get(RedisKey.HS_XC_TOKEN);
            String hm = (String)map.get("phoneNumber");
            String sfIp=redisClient.get(RedisKey.HS_XC_IP); //数据库里查询缓存获得
            try{
                TimeUnit.SECONDS.sleep(10);//程序先等待十秒钟获取验证码
                String url = "http://"+sfIp+":9180/service.asmx/GetYzmStr?token="+token+"&hm="+hm+"&xmid="+xmid;
                yzNum = HttpClientUtil.httpGetRequest(url);
                if(null!=yzNum&&!("").equals(yzNum)){
                  // String paPhone = "2017-08-04 15:26:19收到 您正在注册平安金管家APP，验证码788479，有效期为2分钟，切勿转发或告知他人。【平安人寿】";
                    if(yzNum.length()>20){
                        yzmCode = yzNum.substring(yzNum.indexOf("码")+1,yzNum.indexOf("码")+7);
                    }else{
                        if(yzNum.equals("1")) {
                            TimeUnit.SECONDS.sleep(10);
                            yzNum = HttpClientUtil.httpGetRequest(url);
                            if(yzNum.length()>20){
                                yzmCode = yzNum.substring(yzNum.indexOf("码")+1,yzNum.indexOf("码")+7);
                            }
                        }
                        if(yzNum.equals("0")) {
                            this.getXingChenToken();
                            yzNum = HttpClientUtil.httpGetRequest(url);
                            if(yzNum.length()>20){
                                yzmCode = yzNum.substring(yzNum.indexOf("码")+1,yzNum.indexOf("码")+7);
                            }
                        } else{
                            publicResponse.setSuccess(false);
                            publicResponse.setData(yzNum);
                            return publicResponse;
                        }
                    }
                }else{
                    url = "http://"+sfIp+":9180/service.asmx/GetYzmLogStr?token="+token+"&hm="+hm+"&xmid="+xmid;
                    yzNum = HttpClientUtil.httpGetRequest(url);
                    if(null!=yzNum && yzNum.length()>20){
                        yzmCode = yzNum.substring(yzNum.indexOf("码")+1,yzNum.indexOf("码")+7);
                    }
                }
            } catch (Exception ex) {
                //TODO 如果在使用GetYzmStr函数获取接收到短信或语音验证码时，像网络丢包或程序处理异常出错等情况下，可以使用GetYzmLogStr再次来获取丢失验证码
                try{
                    String url = "http://www.xingchenma.com:9180/service.asmx/GetYzmLogStr?token="+token+"&hm="+hm+"&xmid="+xmid;
                    yzNum = HttpClientUtil.httpGetRequest(url);
                    if(null!=yzNum && yzNum.length()>20){
                        yzmCode = yzNum.substring(yzNum.indexOf("码")+1,yzNum.indexOf("码")+7);
                    }
                }  catch (Exception ex1){
                    logger.info("getYzNumber"+ex1+"yznum"+yzNum);
                    this.getSfNumber(map);
                }
            }
            ppMap.put("yzmCode",yzmCode);
            publicResponse.setData(ppMap);
            publicResponse.setSuccess(true);
            return publicResponse;
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 释放号码
     * @param map
     * @return
     */
    @Override
    public PublicResponse getSfNumber(Map<String, Object> map) {
        PublicResponse publicResponse = new PublicResponse();
        try{
            if(null!=map && null!=map.get("hm")){
                Map<String, Object> ppMap = new HashMap<String, Object>();
                String token = redisClient.get(RedisKey.HS_XC_TOKEN);
                String hm = (String)map.get("phoneNumber");
                String url = "http://www.xingchenma.com:9180/service.asmx/sfHmStr?token="+token+"&hm="+hm;
                String sfPhone = HttpClientUtil.httpGetRequest(url);
                if(null!=sfPhone&&!("").equals(sfPhone)){
                    if(("1").equals(sfPhone)){
                       this.getXingChenToken();
                    }
                    publicResponse.setSuccess(true);
                    publicResponse.setData(sfPhone);
                } else {
                    this.getSfAllPhoneNumber();
                }
                //  ppMap.put("passWord",paPassWord);
            }
        }   catch (Exception ex){
            publicResponse.setSuccess(true);
            publicResponse.setData("sfPhone"+ex);
        }
        return publicResponse;
    }
   @Override
    public PublicResponse getYgNumber(Map<String, Object> map){
       boolean flag = customerService.getCustomerList();
       if(flag){

       } else{
           this
       }
       return null;
    }
    /**
     *  获取项目ID
     * @param obj
     * @return
     */
    private String getXmId(Object obj){
        if(null!=obj&&!("").equals(obj)){
         String   xmid = SanFangStatus.getName(Integer.parseInt((String)obj));
            return xmid;
        }
        return null;
    }

    /**
     * 释放所有号码
     * @return
     */
    private boolean getSfAllPhoneNumber(){
       boolean flag = false;
        String token = redisClient.get(RedisKey.HS_XC_TOKEN);
        String url = "http://www.xingchenma.com:9180/service.asmx/sfAllStr?token="+token;
        String sfAll = HttpClientUtil.httpGetRequest(url);
        if(null!=sfAll && ("1").equals(sfAll)){
             flag=true;
        } else{
             this.getExitSanFangLogin();
        }
        return flag;
    }

    /**
     * 退出登录
     * @return
     */
    private  boolean getExitSanFangLogin(){
        boolean flag =false;
        String token = redisClient.get(RedisKey.HS_XC_TOKEN);
        String url = "http://www.xingchenma.com:9180/service.asmx/UserExitStr?token="+token;
        String sfAll = HttpClientUtil.httpGetRequest(url);
        if(null!=sfAll && ("1").equals(sfAll)){
            redisClient.delete(redisClient.get(RedisKey.HS_XC_TOKEN));
        } else{
            redisClient.delete(redisClient.get(RedisKey.HS_XC_TOKEN));
        }
        return  true;
    }

    /**
     * 登录验证
     * @param
     * @return
     */
    @Override
    public PublicResponse<String> getXingChenToken() {
        PublicResponse<String> publicResponse = new PublicResponse<String>();
        try{
            String token = redisClient.get(RedisKey.HS_XC_TOKEN);
            if(null!=token && !("").equals(token)){
                publicResponse.setData(token);
            } else{
                int id= 1;
                SanFang sInfo = sanFangMapper.getSanFangInfoById(id);
                if(null!=sInfo){
                    String user = sInfo.getSfCode();
                    String passWord = sInfo.getSfPassword();
                    String sIp = sInfo.getSfIp();
                    String url ="http://"+sIp+":9180/service.asmx/UserLoginStr?name="+user+"&psw="+passWord;
                    token =  HttpClientUtil.httpGetRequest(url) ;
                    //三方接口返回是否为空校验
                    if(null!=token && !("").equals(token)){
                        if(token.length()>20){
                            redisClient.set(RedisKey.HS_XC_TOKEN,token);
                            redisClient.set(RedisKey.HS_XC_IP,sIp);
                            publicResponse.setSuccess(true);
                        }else{
                            publicResponse.setSuccess(false);
                        }
                    }else {
                        publicResponse.setSuccess(false);
                    }
                }
                publicResponse.setData(token);
            }
            logger.info("调用接口超时异常"+token);
        } catch (Exception ex){
            publicResponse.setSuccess(false);
            publicResponse.setData("ex"+ex);
            logger.error("getXingChenToken"+ex);
        }
        return  publicResponse;
    }
}
