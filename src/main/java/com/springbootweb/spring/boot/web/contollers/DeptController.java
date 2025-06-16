package com.springbootweb.spring.boot.web.contollers;


import com.springbootweb.spring.boot.web.dto.DeptDTO;
import com.springbootweb.spring.boot.web.services.DeptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/departments")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }


    @GetMapping(path = "/{deptID}")
    public ResponseEntity<DeptDTO> getDeptID(@PathVariable(name = "deptID") long id){

        Optional<DeptDTO> DeptDTO = deptService.getDeptID(id);
        return DeptDTO.map(DeptDTO1 -> ResponseEntity.ok(DeptDTO1))
                .orElseThrow(() -> new NoSuchElementException(" employee not found with id" + id));
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


    @PutMapping(path = "/{deptID}")
    public ResponseEntity<DeptDTO> updateDeptID(@RequestBody @Valid  DeptDTO DeptDTO, @PathVariable(name = "deptID") Long id)
    {
        return ResponseEntity.ok(deptService.updateDeptID(DeptDTO , id));
    }


    @DeleteMapping(path = "/{deptID}" )
    public ResponseEntity<Boolean> deleteDeptID(@PathVariable Long deptID)
    {
        Boolean gotdlt = deptService.deleteDeptID(deptID);
        if(gotdlt) return  ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }


    @PatchMapping(path = "/{deptID}" )
    public ResponseEntity<DeptDTO> partialupdateDeptID(@RequestBody Map< String, Object> updates , @PathVariable(name = "deptID") Long id)
    {
        DeptDTO DeptDTO =deptService.partialupdateDeptID(id , updates);
        if(DeptDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(DeptDTO);
    }
}
