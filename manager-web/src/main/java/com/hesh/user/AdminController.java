package com.hesh.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gaods on 2017/7/18.
 */

@Controller
@RequestMapping("/register")
public class AdminController {


    @RequestMapping("")
    public String register(){
        return "register";
    }
}
