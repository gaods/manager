package com.hesh.sanfang;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.CustomerService;
import com.hesh.service.impl.SanFangxcServiceImpl;
import com.hesh.vo.ResultObjectWebVo;
import com.hesh.vo.user.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaods on 2017/7/31.
 */

@Controller
@RequestMapping("/xingchenma")
public class XingchenmaController {
    private static final Log logger = LogFactory.getLog(XingchenmaController.class);

    @Resource
    SanFangxcServiceImpl sanFangxcService;
    @Resource
    CustomerService customerService;
    /**
     *  获取号码
     * @param param
     * @return
     */
    @RequestMapping("/getPhoneNumberPa")
    public @ResponseBody  String getXingChenToken(@RequestBody Map<String, Object> param){
        try{
            PublicResponse
            ResultObjectWebVo resultObjectWebVo = new ResultObjectWebVo();
            boolean flag =  customerService.getCustomerList();
            if(flag){
                String  result =null;
                 // PublicResponse<String> result=  sanFangxcService.getXingChenToken();
                if(null!=result){
                    Map<String, Object> map =  sanFangxcService.getZcPhonePassWord(param);
                }else{
                    //TODO 退出整个操作提示三方接口有问题

                    resultObjectWebVo.setStatus();
                }
            }else{

            }
        }catch (Exception ex){
            logger.error("调用三方异常"+ex);
        }

        return  JsonUtils.toJson(map);
    }

    /**
     * 验证码
     * @param param
     * @return
     */
    @RequestMapping("/getYzNumber")
    public @ResponseBody  ResultObjectVo getYzNumber(@RequestBody String param){
        ResultObjectVo<String> resultObjectVo = new ResultObjectVo<String>();
        Map<String, Object>  resultmap=sanFangxcService.getYzNumber(JsonUtils.fromJson(param, HashMap.class));
        return  JsonUtils.toJson(resultmap);
    }
    /**
     *
     */


    /**
     * 释放号码
     * 获取 token
     * 然后存起来
     * @param param
     * @return
     */
    @RequestMapping("/getSfNumber")
    public @ResponseBody  String getSfNumber(@RequestBody String param){
        HashMap parammap=  JsonUtils.fromJson(param, HashMap.class);
       String token=sanFangxcService.getXingChenToken();


        Map<String, Object>  resultmap=sanFangxcService.getSfNumber(parammap);

        return  JsonUtils.toJson(resultmap);
    }


}
