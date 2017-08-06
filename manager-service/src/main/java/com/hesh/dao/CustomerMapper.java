package com.hesh.dao;

import com.hesh.vo.user.Customer;

import java.util.List;

/**
 * Created by gaods on 2017/7/30.
 */
public interface CustomerMapper {
    /**
     * 存储数据
     * @param customerlist
     * @return
     */
    public int insertcustomer(Customer customerlist);

    /**
     * 查询list
     * @return
     */
    List<Customer> selectcustomer();

    /***
     * 删除根据主键
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 修改详细信息
     * @param customer
     */
    void updateByPrimaryKeySelective(Customer customer);

    /**
     * 根据主键2
     * @param id
     * @return
     */
    Customer selectById(Integer id);

    /**
     *
     * @param customer
     * @return
     */
    int updateStateByPrimaryKey(Customer customer);
}
