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
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "departments")
public class DeptEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private  long id;
    private  String title;
    @JsonProperty(value = "isactive")
    private boolean isactive;
    private LocalDate createdAt;
}
