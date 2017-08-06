package com.hesh.service;

import com.hesh.vo.user.Customer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-8-5
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerService {
    public boolean getCustomerList();

    public void updateCustomerById(Customer customer);
}
