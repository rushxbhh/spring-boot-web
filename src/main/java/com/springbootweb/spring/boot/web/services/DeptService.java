package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.dto.DeptDTO;
import com.springbootweb.spring.boot.web.dto.EmployeeDTO;
import com.springbootweb.spring.boot.web.entities.DeptEntity;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.repositories.DeptRepository;
import com.springbootweb.spring.boot.web.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeptService {

    private final EmployeeRepository employeeRepository;
    private  final DeptRepository deptRepository;
    private  final ModelMapper modelMapper;

    public DeptService(EmployeeRepository employeeRepository, DeptRepository deptRepository,
                       ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.deptRepository = deptRepository;
        this.modelMapper = modelMapper;

    }

    public Optional<DeptDTO> getDeptID(long departmentid)
    {
        Optional<DeptEntity> deptEntity = deptRepository.findById(departmentid);
        return deptEntity.map(deptEntity1 -> modelMapper.map(deptEntity1, DeptDTO.class));
    }

    public List<DeptDTO> getallDept()
    {
        List<DeptEntity> deptEntities = deptRepository.findAll();
        return deptEntities
                .stream()
                .map(deptEntity -> modelMapper.map(deptEntity, DeptDTO.class))
                .toList();
    }

    public DeptDTO addnewDept(DeptDTO inputnewemp){
        DeptEntity entityToSave = modelMapper.map(inputnewemp, DeptEntity.class);
        DeptEntity saveddeptEntity =deptRepository.save(entityToSave);
        return modelMapper.map(saveddeptEntity, DeptDTO.class);
    }

    public DeptDTO updateDeptID(DeptDTO DeptDTO, Long departmentid) {
        boolean exists = ifexist(departmentid);
        if(!exists) throw new NoSuchElementException(" update request not exist");
        DeptEntity deptEntity = modelMapper.map(DeptDTO , DeptEntity.class);
        deptEntity.setDepartmentid(departmentid);
        DeptEntity saveddeptEntity = deptRepository.save(deptEntity);
        return modelMapper.map(saveddeptEntity, DeptDTO.class);
    }

    public boolean ifexist(Long deptID)  {
        return deptRepository.existsById(deptID);
    }

    public boolean  deleteDeptID(Long deptID) {
        boolean exists = ifexist(deptID);
        if(!exists) throw new NoSuchElementException("deleted request doesnt exist");
        deptRepository.deleteById(deptID);
        return true;
    }

    public DeptDTO partialupdateDeptID(Long deptID, Map<String , Object> update) {
        boolean exists = ifexist(deptID);
        if(!exists) return null;
        DeptEntity deptEntity = deptRepository.findById(deptID).get();
        update.forEach((field, value) -> {
            Field feildToBeupdated = ReflectionUtils.findField(DeptEntity.class , field);
            if(feildToBeupdated != null) {
                feildToBeupdated.setAccessible(true);
                ReflectionUtils.setField(feildToBeupdated, deptEntity, value);
            }
        });
        return modelMapper.map(deptRepository.save(deptEntity) , DeptDTO.class);
    }

    public DeptDTO assignManagertoDept(Long departmentid , Long employeeid)
    {

        DeptEntity deptEntity =deptRepository.findById(departmentid)
                .orElseThrow(() -> new NoSuchElementException(" no such dept"));
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeid)
                .orElseThrow(() -> new NoSuchElementException("no such emp"));

        deptEntity.setManager(employeeEntity);
        DeptEntity updatedDept = deptRepository.save(deptEntity);

        DeptDTO dto = modelMapper.map(updatedDept, DeptDTO.class);
        EmployeeDTO managerDto = modelMapper.map(employeeEntity, EmployeeDTO.class);
        dto.setManager(managerDto); // Set full manager details

        return dto;
    }

    public DeptDTO getDeptbyAssignedManager( Long managerid) {
        Optional<EmployeeEntity> employee = employeeRepository.findById(managerid);
        DeptEntity dept = employee.get().getManagedDepartment();
        if (dept == null) {
            throw new NoSuchElementException("This employee does not manage any department.");
        }
        DeptDTO dto = modelMapper.map(dept, DeptDTO.class);

        // Optional: Clear workers list if you don't want to expose it
        dto.setWorkers(null); // Or dto.setWorkers(Collections.emptyList());

        return dto;
    }

    public DeptDTO assignWorkerToDepartment(Long departmentId, Long employeeId) {
        DeptEntity department = deptRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        if (department.getWorkers().contains(employee)) {
            // Optionally, throw an exception or just return the same DeptDTO
            throw new IllegalStateException("Employee already assigned to this department as a worker.");
        }
        employee.setWorkerdepartment(department);  // set bidirectional relationship
        employeeRepository.save(employee);

        department.getWorkers().add(employee);     // update department's list
        deptRepository.save(department);     // save changes to department (optional if cascade is on)

        return modelMapper.map(department, DeptDTO.class);  // Convert to DTO
    }


}
