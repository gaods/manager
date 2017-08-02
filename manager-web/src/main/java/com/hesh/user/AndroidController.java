package com.hesh.user;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.LoginService;
import com.hesh.web.vo.LoginUser;
import com.hesh.web.vo.MsgVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by gaods on 2017/8/2.
 */

@Controller
@RequestMapping("/ad")
public class AndroidController {

    @Resource
    LoginService loginService;

    @RequestMapping(method = RequestMethod.POST,value = "login")
    public @ResponseBody  MsgVo login(@RequestBody String logininfo) {


        LoginUser user= JsonUtils.fromJson(logininfo,LoginUser.class);

        MsgVo result= loginService.login(user);
        return result;
    }
}
