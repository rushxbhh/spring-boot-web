package com.springbootweb.spring.boot.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {

    private int id;
    @NotNull(message = "Employee ID is required")

@NotNull(message = "emp id is required")
    private Long employeeid;

    @NotNull(message = "Base salary is required")
    @Positive(message = "Base salary must be positive")
    private Double baseSalary;

    @Min(0)
    private Double bonuses = 0.0;

    @Min(0)
    private Double deductions = 0.0;

   private  Double finalSalary;

    private LocalDateTime createdAt;
}

