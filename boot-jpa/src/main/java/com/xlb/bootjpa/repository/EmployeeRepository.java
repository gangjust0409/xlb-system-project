package com.xlb.bootjpa.repository;

import com.xlb.bootjpa.pojo.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

public interface EmployeeRepository extends JpaRepository<Employee, Long>,QuerydslPredicateExecutor<Employee> {

    List<Employee> findByFirstNameLike(String empName);

    List<Employee> findByFirstNameStartingWith(String empName); //模糊匹配：匹配以 ${empName}开头的的

    List<Employee> findByFirstNameEndingWith(String empName); //模糊匹配：匹配以 ${empName} 结尾的

    List<Employee> findByFirstNameContaining(String empName); //模糊匹配：匹配包含 ${empName}的，% ${empName} %

    // order by 查询匹配包含 ${empName}的,按照salary降序排列
    List<Employee> findByFirstNameContainingOrderBySalaryDesc(String empName);

    List<Employee> findByFirstNameContaining(String empName, Pageable pageable);

    List<Employee> findByFirstNameContaining(String empName, Sort sort);

    //使用 stream 流
    @Query("SELECT e FROM Employee e")
    Stream<Employee> findAllByStream();

    //命名参数
    @Query("SELECT e FROM #{#entityName} e WHERE e.firstName = :firstName OR e.lastName = :lastName")
    List<Employee> findByFirstNameOrLastName(@Param("firstName") String firstName, @Param("lastName") String lastName, Sort sort);

    //修改
    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.salary = :salary WHERE e.firstName = :firstName")
    int changeSalary(@Param("firstName") String firstName, @Param("salary") Double salary);





}
