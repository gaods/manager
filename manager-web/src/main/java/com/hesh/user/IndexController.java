package com.hesh.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gaods on 2017/7/16.
 */
@Controller
@RequestMapping("/home")
public class IndexController {
    @RequestMapping("")
    public String index(){
        return "home";
    }
}
