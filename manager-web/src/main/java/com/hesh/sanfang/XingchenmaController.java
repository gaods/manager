package com.hesh.sanfang;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.CustomerService;
import com.hesh.service.EmployeeService;
import com.hesh.service.impl.SanFangxcServiceImpl;
import com.hesh.vo.ResultObjectWebVo;
import com.hesh.vo.user.Customer;
import com.hesh.vo.user.Employee;
import com.hesh.vo.user.PublicResponse;
import com.hesh.web.vo.ResultObjectVo;
import org.apache.commons.lang.StringUtils;
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

    @Resource
    EmployeeService employeeService;
    /**
     *  获取号码
     * @param param
     * @return
     */
    @RequestMapping("/getPhoneNumberPa")
    public @ResponseBody  String getXingChenToken(@RequestBody Map<String, Object> param){
        ResultObjectWebVo resultObjectWebVo = new ResultObjectWebVo();
        try{


            boolean flag =  customerService.getCustomerList();
            if(flag){
                PublicResponse<String> result=  sanFangxcService.getXingChenToken();

                if(null!=result&&result.isSuccess()){

                    PublicResponse response =  sanFangxcService.getZcPhonePassWord(param);

                    resultObjectWebVo.setStatus(String.valueOf(response.isSuccess()));
                    resultObjectWebVo.setData(response.getData());
                }else{
                    //TODO 退出整个操作提示三方接口有问题
                    sanFangxcService.getExitSanFangLogin();
                    resultObjectWebVo.setStatus("1");
                }
            }else{
                resultObjectWebVo.setStatus("0");
                resultObjectWebVo.setMsg("没有员工号");
            }
        }catch (Exception ex){
            logger.error("调用三方异常"+ex);
        }

        return  JsonUtils.toJson(resultObjectWebVo);
    }

    /**
     * 验证码
     * @param param
     * @return
     */
    @RequestMapping("/getYzNumber")
    public @ResponseBody  ResultObjectVo getYzNumber(@RequestBody String param){
        ResultObjectVo<String> resultObjectVo = new ResultObjectVo<String>();
        PublicResponse resultmap=sanFangxcService.getYzNumber(JsonUtils.fromJson(param, HashMap.class));
        return  resultObjectVo;
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
       // String token=sanFangxcService.getXingChenToken();


        PublicResponse  resultmap=sanFangxcService.getSfNumber(parammap);

        return  JsonUtils.toJson(resultmap);
    }


    /**
     * 保存
     * 然后存起来
     * @param param
     * @return
     */
    @RequestMapping("/InsertInfoPa")
    public @ResponseBody  String insertInfoPa(@RequestBody Map<String, Object> param){

        Object phoneNumber=param.get("phoneNumber");
        Object name=param.get("name");
        Object telphone=param.get("telphone");
        Object emno=param.get("emno");
        Object flag=param.get("flag");
        Employee employee=new Employee();
        employee.setFlag(getValueifnul(flag));
        employee.setName(getValueifnul(name));
        employee.setTelphone(getValueifnul(telphone));
        employee.setEmno(getValueifnul(emno));

        PublicResponse response= employeeService.insertpa_employee(employee);

        String result="1";

        //失败
        if(!response.isSuccess()){
            result= "2";
        }
        return result;
    }


    private String getValueifnul(Object val){

        if(val==null)return "";
        return val.toString();
    }




}
