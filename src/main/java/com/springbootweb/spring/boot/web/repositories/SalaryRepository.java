package com.springbootweb.spring.boot.web.repositories;

import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.entities.SalaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {

    @Query("SELECT s FROM SalaryEntity s WHERE s.employee.id = :employeeId ORDER BY s.createdAt DESC")
    List<SalaryEntity> findByEmployeeIdOrderByCreatedAtDesc(@Param("employeeId") Long employeeId);

    Optional<SalaryEntity> findTopByEmployee_EmployeeidOrderByCreatedAtDesc(Long employeeId);
}



