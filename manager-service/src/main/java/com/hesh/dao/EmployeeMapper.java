package com.hesh.dao;

import com.hesh.vo.user.Employee;

import java.util.List;

/**
 * Created by gaods on 2017/8/5.
 */
public interface EmployeeMapper {


   int  insertpa_employee(Employee employee);

   List<Employee> selectemployee(Employee employee);

}
