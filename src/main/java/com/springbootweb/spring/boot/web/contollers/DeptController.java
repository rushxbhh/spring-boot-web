package com.springbootweb.spring.boot.web.contollers;


import com.springbootweb.spring.boot.web.dto.DeptDTO;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.services.DeptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/departments")
public class DeptController {


    private final DeptService deptService;

    public DeptController( DeptService deptService) {

        this.deptService = deptService;
    }


    @GetMapping(path = "/{departmentid}")
    public ResponseEntity<DeptDTO> getDeptID(@PathVariable Long departmentid){

        Optional<DeptDTO> DeptDTO = deptService.getDeptID(departmentid);
        return DeptDTO.map(DeptDTO1 -> ResponseEntity.ok(DeptDTO1))
                .orElseThrow(() -> new NoSuchElementException(" employee not found with id" + departmentid));
    }


    @GetMapping
    public ResponseEntity<List<DeptDTO>> getallDept()
    {

        return ResponseEntity.ok(deptService.getallDept());
    }


    @PostMapping
    public ResponseEntity<DeptDTO> addnewDept(@RequestBody @Valid DeptDTO inputDeptDetail){
        DeptDTO saveemp = deptService.addnewDept(inputDeptDetail);
        return  new ResponseEntity<>(saveemp , HttpStatus.CREATED);
    }


    @PutMapping(path = "/{departmentid}")
    public ResponseEntity<DeptDTO> updateDeptID(@RequestBody @Valid  DeptDTO DeptDTO,
                                                @PathVariable Long departmentid)
    {
        return ResponseEntity.ok(deptService.updateDeptID(DeptDTO , departmentid));
    }


    @DeleteMapping(path = "/{departmentid}" )
    public ResponseEntity<Boolean> deleteDeptID(@PathVariable Long departmentid)
    {
        Boolean gotdlt = deptService.deleteDeptID(departmentid);
        if(gotdlt) return  ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }


    @PatchMapping(path = "/{departmentid}" )
    public ResponseEntity<DeptDTO> partialupdateDeptID(@RequestBody Map< String, Object> updates ,
                                                       @PathVariable Long departmentid)
    {
        DeptDTO DeptDTO =deptService.partialupdateDeptID(departmentid, updates);
        if(DeptDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DeptDTO);
    }


    @PutMapping(path = "/{departmentid}/manager/{employeeid}")
    public ResponseEntity<DeptDTO> assignManagertoDept (@Valid @PathVariable Long departmentid,
                                                    @PathVariable Long employeeid)
    {
        return ResponseEntity.ok(deptService.assignManagertoDept( departmentid ,employeeid));
    }

    @GetMapping("/assigned/{managerid}")
    public ResponseEntity<DeptDTO> getDeptbyAssignedManager(@Valid @PathVariable Long managerid)
    {
        return ResponseEntity.ok(deptService.getDeptbyAssignedManager(managerid));
    }

    @PutMapping(path = "/{departmentId}/worker/{employeeId}")
    public ResponseEntity<DeptDTO> assignWorkerToDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long employeeId
    ) {
        DeptDTO updatedDept = deptService.assignWorkerToDepartment(departmentId, employeeId);
        return ResponseEntity.ok(updatedDept);
    }


}
