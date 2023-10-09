package com.xlb.bootjpa.service.impl;

import com.xlb.bootjpa.pojo.Employee;
import com.xlb.bootjpa.repository.EmployeeRepository;
import com.xlb.bootjpa.repository.common.BaseEntiryRepository;
import com.xlb.bootjpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> queryEmployeesList() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> queryEmployeesListByNameLike(String empName) {
        return employeeRepository.findByFirstNameLike(empName);
    }

    @Override
    public Optional<Employee> getDataById(Long Id) {
        Optional<Employee> employee = Optional.of(new Employee());
        Optional<Employee> optional = employeeRepository.findById(Id);
        return optional;
    }


}
