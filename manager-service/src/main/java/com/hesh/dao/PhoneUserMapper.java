package com.hesh.dao;

import com.hesh.vo.user.PhoneUser;

import java.util.List;

/**
 * Created by gaods on 2017/8/1.
 */
public interface PhoneUserMapper {


    List<PhoneUser> selectphoneUser(PhoneUser phoneUser);

}
