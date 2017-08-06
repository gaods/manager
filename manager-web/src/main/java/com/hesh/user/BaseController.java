package com.hesh.user;

import com.hesh.service.LoginService;
import com.hesh.vo.user.User;

import com.hesh.web.vo.LoginUser;
import com.hesheng.cache.redis.SessionCacheManager;
import com.hesheng.context.InvocationInfoProxy;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class BaseController {
    private static final Logger baseLogger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    public SessionCacheManager baseSessionCacheManager;

    @Resource
    LoginService loginService;




    /**
     * 获取当前用户上下文
     * @return
     */
    public User getCurrentUserData(){
        User user =null;
        String userCode = InvocationInfoProxy.getParameter("username");
        if (userCode.equals("")){
            userCode = InvocationInfoProxy.getParameter("userCode");
        }

        if(userCode !=null) {
            try{
                user = baseSessionCacheManager.getCurUser(userCode, User.class);
            }
            catch(Exception e){
                baseLogger.error("查询用户失败!", e);
            }
            if(user!=null){
                return user;
            }
            else{
                LoginUser loginUser=new LoginUser();
                loginUser.setUsername(userCode);
                user = loginService.mlogin(loginUser);
                try{baseSessionCacheManager.cacheUser(userCode, user);}
                catch(Exception e){
                    baseLogger.error("写入用户redis缓存失败!", e);
                }
                return 	user;
            }
        }
        else {
            return 	user;
        }
    }


}
