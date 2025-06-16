package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.dto.DeptDTO;

import com.springbootweb.spring.boot.web.dto.DeptDTO;
import com.springbootweb.spring.boot.web.entities.DeptEntity;
import com.springbootweb.spring.boot.web.repositories.DeptRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DeptService {

    private  final DeptRepository deptRepository;
    private  final ModelMapper modelMapper;

    public DeptService(DeptRepository deptRepository, ModelMapper modelMapper) {
        this.deptRepository = deptRepository;
        this.modelMapper = modelMapper;

    }

    public Optional<DeptDTO> getDeptID(long id)
    {
        Optional<DeptEntity> deptEntity = deptRepository.findById(id);
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

    public DeptDTO updateDeptID(DeptDTO DeptDTO, Long id) {
        boolean exists = ifexist(id);
        if(!exists) throw new NoSuchElementException(" update request not exist");
        DeptEntity deptEntity = modelMapper.map(DeptDTO , DeptEntity.class);
        deptEntity.setId(id);
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
}
