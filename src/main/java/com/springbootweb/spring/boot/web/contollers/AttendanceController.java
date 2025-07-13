package com.springbootweb.spring.boot.web.contollers;


import com.springbootweb.spring.boot.web.dto.AttendanceDTO;
import com.springbootweb.spring.boot.web.services.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {

        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> saveEmployeeAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        AttendanceDTO saveattendance = attendanceService.saveEmployeeAttendance(attendanceDTO);
        return new ResponseEntity<>(saveattendance , HttpStatus.CREATED);
    }

    @GetMapping("/tattendance/today/{employeeid}")
    public ResponseEntity<AttendanceDTO>  getTodaysAttendance(@PathVariable Long employeeid) {

        Optional<AttendanceDTO> attendanceDTO = attendanceService. getTodaysAttendance(employeeid);
        return attendanceDTO.map(attendanceDTO1 -> ResponseEntity.ok(attendanceDTO1))
                .orElseThrow(() -> new NoSuchElementException("emp not found"));

    }

    @GetMapping(path = "/attendance/history/{employeeid}")
    public ResponseEntity<List<AttendanceDTO>>  getAttendanceHistory(@PathVariable Long employeeid) {

        return ResponseEntity.ok(attendanceService.getAttendanceHistory(employeeid));
    }
}

