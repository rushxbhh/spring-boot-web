package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.dto.EmployeeDTO;
import com.springbootweb.spring.boot.web.dto.SalaryDTO;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.entities.SalaryEntity;
import com.springbootweb.spring.boot.web.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import com.springbootweb.spring.boot.web.repositories.SalaryRepository;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class EmployeeService {

    private  final int size = 5;
    private  final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;
    private final SalaryRepository salaryRepository;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, SalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;

        this.salaryRepository = salaryRepository;
    }
    public EmployeeDTO getEmployeeID(Long employeeid) {
        EmployeeEntity employee = employeeRepository.findById(employeeid)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeid));

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        // Get latest salary
        Optional<SalaryEntity> latestSalary = salaryRepository.findTopByEmployee_EmployeeidOrderByCreatedAtDesc(employeeid);
        if (latestSalary.isPresent()) {
            SalaryDTO salaryDTO = modelMapper.map(latestSalary.get(), SalaryDTO.class);
            salaryDTO.setEmployeeid(employeeid);
            employeeDTO.setLatestsalary(salaryDTO);
        }

        return employeeDTO;
    }


    public List<EmployeeDTO> getallEmp(int page, int size , String sortby)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortby));
        Page<EmployeeEntity> employeeEntities = employeeRepository.findAll(pageable);
        return employeeEntities
                        .stream()
                        .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                        .toList();
    }

    public EmployeeDTO addnewEmp(EmployeeDTO inputnewemp){
        EmployeeEntity entityToSave = modelMapper.map(inputnewemp, EmployeeEntity.class);
        EmployeeEntity savedemployeeEntity =employeeRepository.save(entityToSave);
        return modelMapper.map(savedemployeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeebyid(EmployeeDTO employeeDTO, Long employeeid) {
        boolean exists = ifexist(employeeid);
        if(!exists) throw new NoSuchElementException(" update request not exist");
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO , EmployeeEntity.class);
        employeeEntity.setEmployeeid(employeeid);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean ifexist(Long employeeid)  {

        return employeeRepository.existsById(employeeid);
    }

    public boolean  deleteEmployeeiD(Long employeeiD) {
        boolean exists = ifexist(employeeiD);
        if(!exists) throw new NoSuchElementException("deleted request doesnt exist");
        employeeRepository.deleteById(employeeiD);
        return true;
    }

    public EmployeeDTO updateEmployeeid(Long employeeid, Map<String , Object> update) {
        boolean exists = ifexist(employeeid);
        if(!exists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeid).get();
        update.forEach((field, value) -> {
            Field feildToBeupdated = ReflectionUtils.findField(EmployeeEntity.class , field);
              if(feildToBeupdated != null) {
                feildToBeupdated.setAccessible(true);
                ReflectionUtils.setField(feildToBeupdated, employeeEntity, value);
            }
        });
        return modelMapper.map(employeeRepository.save(employeeEntity) , EmployeeDTO.class);
    }
}
