package com.springbootweb.spring.boot.web.services;
import com.springbootweb.spring.boot.web.dto.AttendanceDTO;
import com.springbootweb.spring.boot.web.entities.AttendanceEntity;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.repositories.AttendanceRepository;
import com.springbootweb.spring.boot.web.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;
    private  final EmployeeRepository employeeRepository;
    public AttendanceService(AttendanceRepository attendanceRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    public AttendanceDTO saveEmployeeAttendance(AttendanceDTO attendanceDTO) {
        // Find employee
        EmployeeEntity employee = employeeRepository.findById(attendanceDTO.getEmployeeid())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + attendanceDTO.getEmployeeid()));

        // Check if attendance already exists for this date
        if (attendanceRepository.existsByEmployee_EmployeeidAndAttendanceDate(
                attendanceDTO.getEmployeeid(), attendanceDTO.getAttendanceDate())) {
            throw new RuntimeException("Attendance already marked for employee " +
                    attendanceDTO.getEmployeeid() + " on " + attendanceDTO.getAttendanceDate());
        }

        // ✅ Create and populate attendance entity
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        attendanceEntity.setEmployee(employee);
        attendanceEntity.setAttendanceDate(attendanceDTO.getAttendanceDate());
        attendanceEntity.setIsPresent(attendanceDTO.getIsPresent());
        attendanceEntity.setCheckInTime(attendanceDTO.getCheckInTime());
        attendanceEntity.setCheckOutTime(attendanceDTO.getCheckOutTime());
        attendanceEntity.setRemarks(attendanceDTO.getRemarks());

        // ✅ Save attendance
        AttendanceEntity savedAttendance = attendanceRepository.save(attendanceEntity);

        // ✅ Convert to DTO
        AttendanceDTO result = modelMapper.map(savedAttendance, AttendanceDTO.class);
        result.setEmployeeid(employee.getEmployeeid());
        return result;
    }


    public Optional<AttendanceDTO> getTodaysAttendance(Long employeeId) {
        // Check if employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found with id: " + employeeId);
        }

        Optional<AttendanceEntity> todaysAttendance = attendanceRepository.findTodaysAttendanceByemployeeid(employeeId);

        if (todaysAttendance.isPresent()) {
            AttendanceDTO attendanceDTO = modelMapper.map(todaysAttendance.get(), AttendanceDTO.class);
            attendanceDTO.setEmployeeid(employeeId);
            return Optional.of(attendanceDTO);
        }

        return Optional.empty();
    }

    public List<AttendanceDTO> getAttendanceHistory(Long employeeId) {
        // Check if employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found with id: " + employeeId);
        }

        List<AttendanceEntity> attendanceHistory = attendanceRepository.findAttendanceHistoryByemployeeid(employeeId);

        return attendanceHistory.stream()
                .map(attendance -> {
                    AttendanceDTO attendanceDTO = modelMapper.map(attendance, AttendanceDTO.class);
                    attendanceDTO.setEmployeeid(employeeId);
                    return attendanceDTO;
                })
                .collect(Collectors.toList());
    }
}



