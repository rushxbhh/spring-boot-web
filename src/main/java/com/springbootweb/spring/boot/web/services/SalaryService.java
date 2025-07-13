package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.dto.EmployeeDTO;
import com.springbootweb.spring.boot.web.dto.SalaryDTO;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.entities.SalaryEntity;
import com.springbootweb.spring.boot.web.repositories.SalaryRepository;
import com.springbootweb.spring.boot.web.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SalaryService {

    private final int size = 5;
    private final ModelMapper modelMapper;
    private final SalaryRepository salaryRepository;
    private  final EmployeeRepository employeeRepository;

    public SalaryService(ModelMapper modelMapper, SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    public SalaryDTO calculateAndSaveSalary(SalaryDTO dto) {

        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeid())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        SalaryEntity salary = new SalaryEntity();
        salary.setEmployee(employee);
        salary.setBaseSalary(dto.getBaseSalary());
        salary.setBonuses(dto.getBonuses());
        salary.setDeductions(dto.getDeductions());
        salary.setFinalSalary(dto.getBaseSalary() + dto.getBonuses() - dto.getDeductions());
        SalaryEntity saved = salaryRepository.save(salary);

        // Convert back to DTO and include employee info
        SalaryDTO  result = modelMapper.map(saved, SalaryDTO.class);
        result.setEmployeeid(employee.getEmployeeid());

        return  result;
    }

    public Optional<SalaryDTO> getLatestSalary(Long employeeid) {
        Optional<SalaryEntity> latestSalary = salaryRepository.findTopByEmployee_EmployeeidOrderByCreatedAtDesc(employeeid);

        if (latestSalary.isPresent()) {
            SalaryDTO salaryDTO = modelMapper.map(latestSalary.get(), SalaryDTO.class);
            salaryDTO.setEmployeeid(employeeid);
            return Optional.of(salaryDTO);
        }

        return Optional.empty();
    }

    public List<SalaryDTO> getSalaryHistory(Long employeeid) {
        return salaryRepository.findByEmployeeIdOrderByCreatedAtDesc(employeeid)
                .stream()
                .map(s -> { SalaryDTO salaryDTO = modelMapper.map(s, SalaryDTO.class);
        salaryDTO.setEmployeeid(employeeid);
        return  salaryDTO;
                })
                .toList();
    }
}


