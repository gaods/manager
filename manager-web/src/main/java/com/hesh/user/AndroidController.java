package com.hesh.user;

import com.alibaba.fastjson.JSONObject;
import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.LoginService;
import com.hesh.utils.WebUtils;
import com.hesh.vo.user.User;
import com.hesh.web.vo.LoginUser;
import com.hesh.web.vo.MsgVo;
import com.hesheng.utils.Base64Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gaods on 2017/8/2.
 */

@Controller
@RequestMapping("/ad")
public class AndroidController {
    public static final int HASH_INTERATIONS = 1024;
    public static final String COOKIES_PATH = "/";
    @Resource
    LoginService loginService;

    @RequestMapping(method = RequestMethod.POST,value = "/getLoginPassPa")
    public @ResponseBody   Map<String,Object> login(@RequestBody String param ,HttpServletResponse response) {

        Map<String,Object> h_param=  WebUtils.covertToMap(param);

        Map<String,Object> map = new HashMap<String,Object>();
        Object UserName= h_param.get("UserName");
        Object PassWord= h_param.get("PassWord");
        LoginUser user=new LoginUser();

                //JsonUtils.fromJson(logininfo,LoginUser.class);
        user.setUsername(UserName==null?null:UserName.toString());


        User result= loginService.mlogin(user);
        String password=PassWord==null?null:PassWord.toString();
        String username=user.getUsername();
        if ( password!= null && result != null) {
            byte[] hashPassword = Digests.sha1(password.getBytes(), Encodes.decodeHex("11"), HASH_INTERATIONS);
            String checkPwd = Encodes.encodeHex(hashPassword);
            if (checkPwd.equals(result.getUserPassword())) {
                String cookieValue = Base64Util.encode(checkPwd.getBytes());

                // 校验成功，写cookie
                HashMap<String, String> cookiesMap = new HashMap<String, String>();
                cookiesMap.put("username", username);
                cookiesMap.put("token", cookieValue);
                // cookiesMap.put("ipu_version_id", user.getUserScope());
                //TODO:根据不同角色判断进入不同页面
                for (Iterator<String> iterator = cookiesMap.keySet().iterator(); iterator.hasNext(); ) {
                    String key = iterator.next();
                    Cookie cookie = new Cookie(key, cookiesMap.get(key));
                    cookie.setPath(COOKIES_PATH);
                    //浏览器关闭时候删除cookie
                    cookie.setMaxAge(-1);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);
                }
                map.put("token",cookieValue);
            } else {
                map.put("errormsg", "用户名或密码有误，请重试！");
            }
        }

        return map;
    }
}
