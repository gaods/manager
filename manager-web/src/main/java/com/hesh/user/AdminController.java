package com.hesh.user;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.LoginService;
import com.hesh.vo.user.Customer;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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

        model.addAttribute("customers",customers);


        ModelAndView mv = new ModelAndView("userlist");
        mv.addObject("customers",customers);
        return  mv;
    }



    @RequestMapping(value = "saveuser",method = RequestMethod.POST)
    public String saveuser(ModelMap model,@RequestBody String formdata){

        List<Customer> customers=  JsonUtils.fromJsonArray(formdata,Customer.class);

        boolean issave=loginService.saveCustomer(customers);

        return "";
    }
}
