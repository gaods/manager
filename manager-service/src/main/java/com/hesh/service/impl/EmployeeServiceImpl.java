package com.hesh.service.impl;

import com.hesh.common.utils.json.JsonUtils;
import com.hesh.dao.EmployeeMapper;
import com.hesh.service.EmployeeService;
import com.hesh.vo.user.Employee;
import com.hesh.vo.user.PublicResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gaods on 2017/8/5.
 */
@Service
public class EmployeeServiceImpl  implements EmployeeService {


    @Resource
    EmployeeMapper employeeMapper;

    /**
     * 要校验是否重复
     * 工号是否重复
     * @param employee
     * @return
     */
   public PublicResponse  insertpa_employee(Employee employee){
       PublicResponse result=new PublicResponse();
       try{

           Employee employeetemp=new Employee();
           employeetemp.setTelphone(employee.getTelphone());
           List<Employee> employeeList=employeeMapper.selectemployee(employeetemp);
           if(employeeList==null||employeeList.isEmpty()){
               employeeMapper.insertpa_employee(employee);

           }
           result.setSuccess(true);
       }catch (Exception ex){
           result.setSuccess(false);
           result.setErrorInfos(ex.getMessage());
       }


        return result;
    }
}
