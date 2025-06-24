package com.springbootweb.spring.boot.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Table( name = "departments")
public class DeptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentid;

    private String title;

    @JsonProperty(value = "isactive")
    private boolean isactive;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private EmployeeEntity manager;

    private LocalDate createdAt;
    @OneToMany(mappedBy = "workerdepartment")
    @JsonIgnore
    private Set<EmployeeEntity> workers;


}