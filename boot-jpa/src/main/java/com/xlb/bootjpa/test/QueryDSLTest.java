package com.xlb.bootjpa.test;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xlb.bootjpa.dto.EmployeeDepartmentDto;
import com.xlb.bootjpa.pojo.Employee;
import com.xlb.bootjpa.pojo.QDepartment;
import com.xlb.bootjpa.pojo.QEmployee;
import com.xlb.bootjpa.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@Component
public class QueryDSLTest {

    private QEmployee employee = QEmployee.employee;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private EmployeeRepository employeeRepository;

    //普通查询
    public void simpleQuery(){
        QEmployee employee = QEmployee.employee;
        BooleanExpression eq = employee.employeeId.eq(18L);
        //构建条件
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(eq);
        builder.or(employee.firstName.eq("Ava"));

        employeeRepository.findAll(builder).forEach(System.err::println);
    }

    //返回
    public void orderQueryEMP(){
        QEmployee employee = QEmployee.employee;
        OrderSpecifier<Double> orderSpecifier = new OrderSpecifier<>(Order.DESC, employee.salary);
        employeeRepository.findAll(orderSpecifier).forEach(System.err::println);
    }

    public void select(){
        List<Tuple> fetch = queryFactory.select(employee.firstName, employee.lastName, employee.salary).from(employee).fetch();
        fetch.forEach(System.err::println);
    }


    //多表查询
    public void tables(){
        //得到两张表
        QDepartment department = QDepartment.department;
        List<EmployeeDepartmentDto> departmentDtoList = queryFactory.select(Projections.bean(
                EmployeeDepartmentDto.class,
               employee.employeeId,employee.firstName,employee.lastName,employee.salary,employee.hireDate,department.departmentName
        )).from(employee).leftJoin(department).on(employee.departmentId.eq(department.departmentId)).where().fetch();
        System.err.println(departmentDtoList);
        departmentDtoList.forEach(System.err::println);

    }

    //MySQL聚合函数
    public void mysqlFunction(){
        //计算这些人平均工资
        Double avg = queryFactory.select(employee.salary.avg()).from(employee).fetchOne();
        //总工资
        Double sum = queryFactory.select(employee.salary.sum()).from(employee).fetchOne();
        //人数
        Long count = queryFactory.select(employee.employeeId.count()).from(employee).fetchOne();
        //降序排列
        queryFactory.selectFrom(employee).orderBy(new OrderSpecifier<>(Order.ASC,employee.employeeId)).fetch().forEach(System.err::println);
        System.out.println("===============================================");
        System.err.println("平均：" + new BigDecimal(avg).setScale(2, BigDecimal.ROUND_HALF_UP));
        System.err.println("总" + sum);
        System.err.println("人数：" + count);
    }












    public void main() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       this.tables();
        System.err.println("执行成功！");
    }
}
