package com.springbootweb.spring.boot.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDTO {

    private long id;

    @NotBlank( message = "Name can not be blank")
    @Size(min = 3 ,max = 10, message = "must be in this range")
    private String name;

    @NotBlank( message = "Name can not be blank")
    @Email(message = " should be a valid email")
    private String email;

    @NotNull(message = "age must be filled")
    @Min(value = 18 , message = "above than 18")
    @Max(value = 60 , message =  "must below than 18")
    private int age;

    @NotNull(message = "salary must be filled")
    @Positive(message = "must be in positive number")
    @Digits(integer = 5 , fraction = 2, message = "must be in this range")
    @DecimalMax( value = "10000.99")
    @DecimalMin( value = "1000.99")
    private Double salary;

    @JsonProperty(value = "isactive")
    private boolean isactive;

    @PastOrPresent(message = "should not be in the future")
    private LocalDate joining;

    @NotBlank( message = "role must be filled")
    @Pattern(regexp = "^(ADMIN|USER)$" , message = " must be admin or usern ")
    private String role;



}




