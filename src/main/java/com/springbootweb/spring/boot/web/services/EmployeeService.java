package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.dto.EmployeeDTO;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.repositories.EmployeeRepository;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class EmployeeService {

    private  final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;

    }
    public Optional<EmployeeDTO> getEmployeeID(long id)
    {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1 , EmployeeDTO.class));
    }

    public List<EmployeeDTO> getallEmp()
    {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
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

    public EmployeeDTO updateEmployeebyid(EmployeeDTO employeeDTO, Long id) {
        boolean exists = ifexist(id);
        if(!exists) throw new NoSuchElementException(" update request not exist");
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO , EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean ifexist(Long employeeiD)  {
        return employeeRepository.existsById(employeeiD);
    }

    public boolean  deleteEmployeeiD(Long employeeiD) {
        boolean exists = ifexist(employeeiD);
        if(!exists) throw new NoSuchElementException("deleted request doesnt exist");
        employeeRepository.deleteById(employeeiD);
        return true;
    }

    public EmployeeDTO updateEmployeeid(Long employeeiD, Map<String , Object> update) {
        boolean exists = ifexist(employeeiD);
        if(!exists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeiD).get();
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
