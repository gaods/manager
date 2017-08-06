package com.hesh.user;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.service.LoginService;

import com.hesh.vo.user.User;
import com.hesh.web.vo.LoginUser;
import com.hesh.web.vo.MsgVo;
import com.hesheng.utils.Base64Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by gaods on 2017/7/19.
 */

@Controller
@RequestMapping("/")
public class LoginController {

    public static final int HASH_INTERATIONS = 1024;
    public static final String COOKIES_PATH = "/";
    @Resource
    LoginService loginService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("portaltype", "purchase");
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST,value = "login")
    public @ResponseBody   MsgVo login( LoginUser logininfo , HttpServletResponse response) {

            //model.addAttribute("portaltype", "purchase");
            Map<String, Object> map = new HashMap<String, Object>();
            MsgVo msgVo=new MsgVo();
//            Object UserName = param.get("UserName");
//            Object PassWord = param.get("PassWord");
           // LoginUser user = JsonUtils.fromJson(logininfo,LoginUser.class);
           // user.setUsername(UserName == null ? null : UserName.toString());


        if(logininfo.getUsername()==null){

        }
        if(logininfo.getPassword()==null){

        }
            User result = loginService.mlogin(logininfo);
            String password = logininfo.getPassword();
            String username = logininfo.getUsername();
            if (password != null && result != null) {
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
                    msgVo.setIssuccess("true");
                } else {
                    map.put("errormsg", "用户名或密码有误，请重试！");
                    msgVo.setIssuccess("false");
                    msgVo.setMsg("用户名或密码有误，请重试！");
                }
            }


        return msgVo;
    }

  @RequestMapping(method = RequestMethod.GET,value = "caslogout")
    public String caslogout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
       // model.addAttribute("portaltype", "purchase");
        return "login";
    }

}
