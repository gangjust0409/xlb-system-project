package com.xlb.bootjpa;

import com.xlb.bootjpa.pojo.Department;
import com.xlb.bootjpa.pojo.Employee;
import com.xlb.bootjpa.repository.DepartmentRepository;
import com.xlb.bootjpa.repository.EmployeeRepository;
import com.xlb.bootjpa.service.DepartmentService;
import com.xlb.bootjpa.service.EmployeeService;
import com.xlb.bootjpa.test.QueryDSLTest;
import lombok.extern.slf4j.Slf4j;
import net.minidev.asm.Accessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.Access;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
class BootJpaApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private QueryDSLTest dslTest;

    @Test
    void testEmployee(){
//        employeeService.queryEmployeesList().forEach(System.err::println);
        Department department = new Department();
        department.setDepartmentName("开吧");
        Department savedDepartment = departmentRepository.save(department);
        System.err.println(savedDepartment);
    }

    //学习使用 JPA queryDsl 语句
    @Test
    void studyJPAQueryDslTest() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        dslTest.main();
    }

    @Test
    void contextLoads() {
        //查询所有
       List<Employee> employees = employeeService.queryEmployeesList();
        Stream<Employee> employeeStream = employees.parallelStream();
        DoubleSummaryStatistics statis = employeeStream.mapToDouble(Employee::getSalary).summaryStatistics();
        System.err.println("最大：" + statis.getMax());
        System.err.println("最小：" + statis.getMin());
        System.err.println("平均：" + statis.getAverage());
        System.err.println("sub：" + statis.getSum());
        System.err.println("count：" + statis.getCount());
    }

    @Test
    void queryEmployeeByNameLike(){
       List<Employee> employees = employeeService.queryEmployeesListByNameLike("Jackson");
        employees.forEach(emp -> {
            System.err.println(emp);
        });
    }

    @Test
    void getDataById(){
        /*Optional<Employee> dataById = employeeService.getDataById(25L);
        System.err.println(dataById.get());*/
    }

    @Test
    void testJavascript() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine js = scriptEngineManager.getEngineByName("js");
        js.eval("print('123456')");
    }

    @Test
    void tables(){
        Example<Employee> young = Example.of(new Employee().setFirstName("Samuel"));
        employeeRepository.findAll(young).forEach(row -> {
            System.err.println(String.format("employee ---> %s",row));
        });
    }

    @Test
    void empLike(){
        employeeRepository.findByFirstNameEndingWith("a").forEach(System.err::println);
    }

    //order by
    @Test
    void testOrderBy(){
//        List<Employee> all = employeeRepository.findByFirstNameContainingOrderBySalaryDesc("o");
//        all.forEach(System.err::println);
    }

    /**
     * 按照 salary 字段降序排列，查询所有的员工
     */
    @Test
    void testOrderByBianMa(){
        Sort sort = Sort.by("salary").descending();
//        employeeRepository.findAll(sort).forEach(System.err::println);
    }

    /**
     * 分页
     */
    @Test
    void pagtion(){
        Pageable pageable = Pageable.ofSize(10);
//        employeeRepository.findAll(pageable).forEach(System.err::println);
    }

    @Test
    void testMethodNameQueryEmp(){
        Pageable pageable = Pageable.ofSize(5);
//        employeeRepository.findByFirstNameContaining("o", pageable).forEach(System.err::println);
    }

    @Test
    void testSort(){
        Sort sort = Sort.by("employeeId").ascending();
//        employeeRepository.findByFirstNameContaining("o", sort).forEach(System.err::println);
    }

    /**
     * 使用 stream 流查询，需标注 @Transactional注解，查询完需要关闭 流
     */
    @Transactional
    @Test
    void testQueryEmployeeByStream(){
        employeeRepository.findAllByStream().forEach(System.err::println);
    }

    /**
     * 命名参数
     */
    @Test
    void testNamespacedParams(){
//        employeeRepository.findByFirstNameOrLastName("Aria", "Adams", Sort.by("salary").ascending()).forEach(System.err::println);
    }

    //修改
    @Test
    void modifyEmployee(){
        int result = employeeRepository.changeSalary("Samuel", 65000.00);
        System.err.println(result + " row");
    }

    





















}
