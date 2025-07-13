package com.springbootweb.spring.boot.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springbootweb.spring.boot.web.entities.SalaryEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDTO {

    private Long employeeid;

    @NotBlank( message = "Name can not be blank")
    @Size(min = 3 ,max = 10, message = "must be in this range")
    private String name;

    @NotBlank( message = "Name can not be blank")
    @Email(message = " should be a valid email")
    private String email;

    @NotNull(message = "age must be filled")
    @Min(value = 18 , message = "above than 18")
    @Max(value = 60 , message =  "must below than 60")
    private int age;

   private SalaryDTO latestsalary;

    @JsonProperty(value = "isactive")
    private boolean isactive;

    @PastOrPresent(message = "should not be in the future")
    private LocalDate joining;

    private  String username;

    private List<SalaryDTO> salaries;

    private AttendanceDTO todaysAttendance;


}




