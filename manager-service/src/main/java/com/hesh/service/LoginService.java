package com.hesh.service;

import com.hesh.dao.UserDao;
import com.hesh.dao.UserMapper;
import com.hesh.vo.user.UserVO;
import com.hesh.web.vo.LoginUser;
import com.hesh.web.vo.MsgVo;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

/**
 * Created by gaods on 2017/7/23.
 */



@Service
public class LoginService {

    @Resource
    UserMapper userMapper;

    public MsgVo login(LoginUser user){

        MsgVo msgVo=new MsgVo();

        UserVO userVO=userMapper.selectByLoginName(user.getUsername());

        if(userVO==null){
            msgVo.setIssuccess("false");
            msgVo.setMsg("没有该用户");
            return msgVo;
        }
        if(!user.getPassword().equals(userVO.getPassword())){
            msgVo.setIssuccess("false");
            msgVo.setMsg("密码不正确");
            return msgVo;
        }

        msgVo.setIssuccess("true");
        msgVo.setMsg("登陆成功");
        return msgVo;
    }

}
