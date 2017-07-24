package com.hesh.dao;

import com.hesh.vo.user.UserVO;

/**
 * Created by gaods on 2017/7/24.
 */
public interface UserMapper {


    UserVO selectByLoginName(String userName);
}
