package com.hesh.dao;

import com.hesh.vo.user.Customer;

import java.util.List;

/**
 * Created by gaods on 2017/7/30.
 */
public interface CustomerMapper {

    public int insertcustomer(Customer customerlist);

    List<Customer> selectcustomer();

    int deleteById(Integer id);

    Customer selectById(Integer id);

    int updateStateByPrimaryKey(Customer customer);
}
