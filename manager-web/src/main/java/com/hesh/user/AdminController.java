package com.hesh.user;

import com.hesh.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by gaods on 2017/7/18.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Resource
    LoginService loginService;

    @RequestMapping("")
    public String register(){
        return "register";
    }

    @RequestMapping("userlist")
    public String userlist(){


        return "userlist";
    }
}
