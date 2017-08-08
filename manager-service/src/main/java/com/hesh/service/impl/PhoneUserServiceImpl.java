package com.hesh.service.impl;

import com.hesh.dao.PhoneUserMapper;
import com.hesh.service.PhoneUserService;
import com.hesh.vo.user.PhoneUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-8-6
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
@Service("phoneUserService")
public class PhoneUserServiceImpl implements PhoneUserService {
    @Resource
    PhoneUserMapper phoneUserMapper;

    @Override
    public void insertPhoneUserService(PhoneUser phoneUser) {
        phoneUser.setDr(0);
        phoneUser.setCreateTime(new Date());
        phoneUserMapper.insertpaPhoneUser(phoneUser);
    }
}
