package com.xlb.bootjpa.service;

import com.xlb.bootjpa.pojo.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> queryEmployeesList();

    List<Employee> queryEmployeesListByNameLike(String empName);

    Optional<Employee> getDataById(Long Id);

}
