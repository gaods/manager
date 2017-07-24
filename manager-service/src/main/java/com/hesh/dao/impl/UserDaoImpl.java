package com.hesh.dao.impl;

import com.hesh.dao.UserDao;
import com.hesh.vo.user.UserVO;
import com.hesh.web.vo.LoginUser;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by gaods on 2017/7/24.
 */

public class UserDaoImpl  extends SqlSessionDaoSupport implements UserDao {
    @Override
    public UserVO findUserByCriteria(LoginUser loginUser) {
        return null;
    }
}
