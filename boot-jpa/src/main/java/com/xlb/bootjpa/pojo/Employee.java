package com.xlb.bootjpa.pojo;

import com.querydsl.core.annotations.QueryEntities;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Accessors(chain = true)
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String firstName;
    private String lastName;

    private Long departmentId;
    //日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date hireDate;
    private Double salary;

//  表示日期类型，只能标注在参数上  @Temporal(TemporalType.TIMESTAMP) jpa 下
    //日期类型  javax下的
    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // 标注不是数据库中的字段
    @Transient
    private List<Department> departmentList;
}