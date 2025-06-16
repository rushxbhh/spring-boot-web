package com.springbootweb.spring.boot.web.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "emolyees")


public class EmployeeEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private int age;
    private Double salary;
    @JsonProperty(value = "isactive")
    private boolean isactive;
    private LocalDate joining;
    private String role;

}
