package com.springbootweb.spring.boot.web.contollers;

import com.springbootweb.spring.boot.web.dto.SalaryDTO;
import com.springbootweb.spring.boot.web.services.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salaries")
public class SalaryController {    private final  SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/calandsave")
    public ResponseEntity<SalaryDTO> calculateAndSaveSalary(@RequestBody SalaryDTO dto) {
        SalaryDTO saved = salaryService.calculateAndSaveSalary(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/latest/{employeeid}")
    public ResponseEntity<Optional<SalaryDTO>> getLatest(@PathVariable Long employeeid) {
        return ResponseEntity.ok(salaryService.getLatestSalary(employeeid));
    }

    @GetMapping("/history/{employeeid}")
    public ResponseEntity<List<SalaryDTO>> getHistory(@PathVariable Long employeeid) {
        return ResponseEntity.ok(salaryService.getSalaryHistory(employeeid));
    }

    }

