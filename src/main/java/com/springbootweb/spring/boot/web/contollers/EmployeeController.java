package com.springbootweb.spring.boot.web.contollers;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.springbootweb.spring.boot.web.dto.EmployeeDTO;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{employeeiD}")
   public ResponseEntity<EmployeeDTO> getEmployeeID(@PathVariable(name = "employeeiD") long id){

         Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeID(id);
         return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                 .orElseThrow(() -> new NoSuchElementException(" employee not found with id" + id));
    }


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getallEmp()
    {
        return ResponseEntity.ok(employeeService.getallEmp());
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> addnewEmp(@RequestBody @Valid EmployeeDTO inputnewemp){
       EmployeeDTO saveemp = employeeService.addnewEmp(inputnewemp);
        return  new ResponseEntity<>(saveemp , HttpStatus.CREATED);
    }


    @PutMapping(path = "/{employeeiD}")
    public ResponseEntity<EmployeeDTO> updateEmployeebyid(@RequestBody @Valid  EmployeeDTO employeeDTO, @PathVariable(name = "employeeiD") Long id)
    {
      return ResponseEntity.ok(employeeService.updateEmployeebyid(employeeDTO , id));
    }


    @DeleteMapping(path = "/{employeeiD}" )
    public ResponseEntity<Boolean> deleteEmployeeid(@PathVariable Long employeeiD)
    {
        Boolean gotdlt = employeeService.deleteEmployeeiD(employeeiD);
        if(gotdlt) return  ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }


    @PatchMapping(path = "/{employeeiD}" )
    public ResponseEntity<EmployeeDTO> updateEmployeeid(@RequestBody Map< String, Object> updates , @PathVariable(name = "employeeiD") Long id)
    {
        EmployeeDTO employeeDTO =employeeService.updateEmployeeid(id , updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

}
