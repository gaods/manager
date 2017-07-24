package com.hesh.dao;

import com.hesh.vo.user.UserVO;
import com.hesh.web.vo.LoginUser;

/**
 * Created by gaods on 2017/7/23.
 */
public interface UserDao {


    UserVO findUserByCriteria(LoginUser loginUser);

}
