package com.springbootweb.spring.boot.web.contollers;
import java.util.List;
import java.util.Map;

import com.springbootweb.spring.boot.web.dto.EmployeeDTO;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{employeeid}")
   public ResponseEntity<EmployeeDTO> getEmployeeID(@PathVariable(name = "employeeid") long id , @AuthenticationPrincipal EmployeeEntity user) {

      //  User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user" , user);
        EmployeeDTO employeeDTO = employeeService.getEmployeeID(id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getallEmp( @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @RequestParam(defaultValue = "employeeid") String sortby)
    {
        return ResponseEntity.ok(employeeService.getallEmp(page , size , sortby));
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> addnewEmp(@RequestBody @Valid EmployeeDTO inputnewemp){
       EmployeeDTO saveemp = employeeService.addnewEmp(inputnewemp);
        return  new ResponseEntity<>(saveemp , HttpStatus.CREATED);
    }


    @PutMapping(path = "/{employeeid}")
    public ResponseEntity<EmployeeDTO> updateEmployeebyid(@RequestBody @Valid  EmployeeDTO employeeDTO, @PathVariable(name = "employeeid") Long id)
    {
      return ResponseEntity.ok(employeeService.updateEmployeebyid(employeeDTO , id));
    }


    @DeleteMapping(path = "/{employeeid}" )
    public ResponseEntity<Boolean> deleteEmployeeid(@PathVariable Long employeeid)
    {
        Boolean gotdlt = employeeService.deleteEmployeeiD(employeeid);
        if(gotdlt) return  ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }


    @PatchMapping(path = "/{employeeid}" )
    public ResponseEntity<EmployeeDTO> updateEmployeeid(@RequestBody Map< String, Object> updates , @PathVariable(name = "employeeid") Long id)
    {
        EmployeeDTO employeeDTO =employeeService.updateEmployeeid(id , updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

}
