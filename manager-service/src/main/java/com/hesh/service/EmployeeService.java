package com.hesh.service;

import com.hesh.vo.user.Employee;
import com.hesh.vo.user.PublicResponse;

/**
 * Created by gaods on 2017/8/5.
 */
public interface EmployeeService {

    PublicResponse insertpa_employee(Employee employee);
}
