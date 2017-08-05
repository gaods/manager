package com.hesh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.hesh.dao.CustomerMapper;
import com.hesh.service.CustomerService;
import com.hesh.utils.RedisClient;
import com.hesh.vo.user.Customer;
import com.hesh.vo.user.RedisKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-8-5
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
@Service("customerService")
public class CustomerServiceImpl  implements CustomerService {
    private static final Log logger = LogFactory.getLog(CustomerServiceImpl.class);

    @Resource
    CustomerMapper customerMapper;
    @Resource
    RedisClient redisClient;


    @Override
    public boolean getCustomerList() {
        boolean flag = false;
        try{
            String customer =  redisClient.get(RedisKey.HS_CUSTOMER);
            if(null!=customer){
                flag = true;
            }  else{
                List<Customer> list =  customerMapper.selectcustomer();
                if(null!=list && list.size()>0){
                    redisClient.set(RedisKey.HS_CUSTOMER, JSONObject.toJSONString(list.get(0)));
                    flag = true;
                }
            }
        }  catch (Exception ex){
            logger.info("getCustomerList"+ex);
        }
        return flag;
    }
}
