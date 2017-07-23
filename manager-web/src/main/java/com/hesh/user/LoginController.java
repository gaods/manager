package com.hesh.user;

import com.hesh.service.LoginService;

import com.hesh.web.vo.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by gaods on 2017/7/19.
 */

@Controller
@RequestMapping("/")
public class LoginController {


    @Resource
    LoginService loginService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("portaltype", "purchase");
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST,value = "login")
    public String login(ModelMap model, LoginUser user) {
        model.addAttribute("portaltype", "purchase");

       String result= loginService.login(user);
        return "home";
    }

}
