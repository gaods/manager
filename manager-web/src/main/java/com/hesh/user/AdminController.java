package com.hesh.user;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.LoginService;
import com.hesh.vo.user.Customer;
import com.hesh.vo.user.PhoneUser;
import com.hesh.web.vo.ResultObjectVo;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gaods on 2017/7/18.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Resource
    LoginService loginService;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/adduser")
    public String adduser(){
        return "adduser";
    }

    @RequestMapping("userlist")
    public ModelAndView userlist(ModelMap model){
        List<Customer> customers= loginService.getCusomerlist();
        ModelAndView mv = new ModelAndView("userlist1");
        mv.addObject("customers",customers);

        return  mv;
    }



    @RequestMapping(value = "saveuser",method = RequestMethod.POST)
    public @ResponseBody  ResultObjectVo saveuser(ModelMap model, @RequestBody String formdata){

        List<Customer> customers=  JsonUtils.fromJsonArray(formdata,Customer.class);

        boolean issave=loginService.saveCustomer(customers);

        ResultObjectVo resultObjectVo=new ResultObjectVo();

        if(issave){
            resultObjectVo.setStatus(1);
        }else{
            resultObjectVo.setStatus(0);
        }



        return  resultObjectVo;
    }



    @RequestMapping("/showuser")
    public @ResponseBody  ResultObjectVo showuser(@RequestBody String id){


       if(StringUtils.isEmpty("id"))
           return null;
        String idtemp=id;
        if(id.indexOf('=')>0)
            idtemp=id.substring(0,id.length()-1);

        List<PhoneUser> userlist= loginService.getPhoneUserlistByCustomId(Integer.valueOf(idtemp));

        ResultObjectVo resultObjectVo=new ResultObjectVo();

        if(!userlist.isEmpty()){
            resultObjectVo.setStatus(1);
            resultObjectVo.setData(userlist);
        }else{
            resultObjectVo.setStatus(0);
        }
        return  resultObjectVo;
    }


    @RequestMapping("/deleteuser")
    public @ResponseBody  ResultObjectVo deleteuser(@RequestBody String id){


        if(StringUtils.isEmpty("id"))
            return null;
        String idtemp=id;
        if(id.indexOf('=')>0)
            idtemp=id.substring(0,id.length()-1);

       boolean issave  =loginService.deleteCustomById(Integer.valueOf(idtemp));

     ResultObjectVo resultObjectVo=new ResultObjectVo();

        if(issave){
            resultObjectVo.setStatus(1);
        }else{
            resultObjectVo.setStatus(0);
        }
        return  resultObjectVo;
    }
}
