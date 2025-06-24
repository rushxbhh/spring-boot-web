package com.springbootweb.spring.boot.web.repositories;

import com.springbootweb.spring.boot.web.entities.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    @Query("SELECT a FROM AttendanceEntity a WHERE a.employee.id = :employeeid AND a.attendanceDate = :date")
    Optional<AttendanceEntity> findByemployeeidAndDate(@Param("employeeid") Long employeeid, @Param("date") LocalDate date);

    // Find today's attendance for an employee
    @Query("SELECT a FROM AttendanceEntity a WHERE a.employee.id = :employeeid AND a.attendanceDate = CURRENT_DATE")
    Optional<AttendanceEntity> findTodaysAttendanceByemployeeid(@Param("employeeid") Long employeeid);

    // Find attendance history for an employee (ordered by date desc)
    @Query("SELECT a FROM AttendanceEntity a WHERE a.employee.id = :employeeid ORDER BY a.attendanceDate DESC")
    List<AttendanceEntity> findAttendanceHistoryByemployeeid(@Param("employeeid") Long employeeid);

    // Check if attendance already exists for employee on specific date
    boolean existsByEmployee_EmployeeidAndAttendanceDate(Long employeeid, LocalDate attendanceDate);

}

