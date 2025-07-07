package com.springbootweb.spring.boot.web.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "employeeid")
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "employees")


public class EmployeeEntity extends  AuditableEntity
{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long employeeid;

    private String name;
    private String email;
    private int age;
    @JsonProperty(value = "isactive")
    private boolean isactive;
    private LocalDate joining;
    private String role;

    @OneToOne(mappedBy = "manager")
    @JsonIgnore
    private DeptEntity managedDepartment;

    @ManyToOne
    @JoinColumn( name = "Worker_dept_id" , referencedColumnName = "departmentid")
    private  DeptEntity workerdepartment;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SalaryEntity> salaries;

    // OneToMany relationship with Attendance
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttendanceEntity> attendances;


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeEntity that)) return false;
        return Objects.equals(getEmployeeid(), that.getEmployeeid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmployeeid());
    }
}
