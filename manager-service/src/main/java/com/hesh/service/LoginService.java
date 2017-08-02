package com.hesh.service;

import com.hesh.dao.CustomerMapper;
import com.hesh.dao.PhoneUserMapper;
import com.hesh.dao.UserMapper;
import com.hesh.vo.user.Customer;
import com.hesh.vo.user.PhoneUser;
import com.hesh.vo.user.User;
import com.hesh.web.vo.LoginUser;
import com.hesh.web.vo.MsgVo;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gaods on 2017/7/23.
 */



@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Resource
    UserMapper userMapper;

    @Resource
    CustomerMapper customerMapper;

    @Resource
    PhoneUserMapper phoneUserMapper;

    public MsgVo login(LoginUser user){

        MsgVo msgVo=new MsgVo();
        User user1=new User();
        user1.setUserName(user.getUsername());
        User userVO=userMapper.selectuser(user1);

        if(userVO==null){
            msgVo.setIssuccess("false");
            msgVo.setMsg("没有该用户");
            return msgVo;
        }
        if(!user.getPassword().equals(userVO.getUserPassword())){
            msgVo.setIssuccess("false");
            msgVo.setMsg("密码不正确");
            return msgVo;
        }

        msgVo.setIssuccess("true");
        msgVo.setMsg("登陆成功");
        return msgVo;
    }

    public boolean   saveCustomer(List<Customer> customerlist){
        for(Customer customer:customerlist){
            customerMapper.insertcustomer(customer);
        }

        return true;
    }

    public List<Customer> getCusomerlist(){


        return customerMapper.selectcustomer();

    }

    public List<PhoneUser> getPhoneUserlistByCustomId(Integer id){
        PhoneUser phoneUser=new PhoneUser();
        phoneUser.setCustomerid(id);
       return  phoneUserMapper.selectphoneUser(phoneUser);
    }


    public boolean deleteCustomById(Integer id){
        int i=customerMapper.deleteById(id);
        logger.info("删除🆔id"+id+"数量"+i);
      return   i>0;
    }
}
