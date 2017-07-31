package com.hesh.sanfang;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.impl.SanFangxcServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaods on 2017/7/31.
 */

@Controller
@RequestMapping("/xingchenma")
public class XingchenmaController {

    @Resource
    SanFangxcServiceImpl sanFangxcService;

    @RequestMapping("/getZcPhonePassWord")
    public @ResponseBody  String getZcPhonePassWord(@RequestBody String param){

        Map<String, Object>  resultmap=sanFangxcService.getZcPhonePassWord(JsonUtils.fromJson(param, HashMap.class));

        return  JsonUtils.toJson(resultmap);
    }



    @RequestMapping("/getYzNumber")
    public @ResponseBody  String getYzNumber(@RequestBody String param){

        Map<String, Object>  resultmap=sanFangxcService.getYzNumber(JsonUtils.fromJson(param, HashMap.class));

        return  JsonUtils.toJson(resultmap);
    }

    @RequestMapping("/getSfNumber")
    public @ResponseBody  String getSfNumber(@RequestBody String param){

        Map<String, Object>  resultmap=sanFangxcService.getSfNumber(JsonUtils.fromJson(param, HashMap.class));

        return  JsonUtils.toJson(resultmap);
    }


    @RequestMapping("/getXingChenToken")
    public @ResponseBody  String getXingChenToken(@RequestBody String param){

        String result=sanFangxcService.getXingChenToken(JsonUtils.fromJson(param, HashMap.class));

        return  result;
    }
}
