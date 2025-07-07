package com.springbootweb.spring.boot.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DeptDTO {

    private Long departmentid;

    @NotBlank( message = " title can not be blank")
    @Pattern(regexp = "^(HR|IT|social|servents)$" , message = " must be in these titles")
    private  String title;

    @JsonProperty(value = "isactive")
    private  boolean isactive;

    private EmployeeDTO manager;

    private Set<EmployeeDTO> workers;

}
