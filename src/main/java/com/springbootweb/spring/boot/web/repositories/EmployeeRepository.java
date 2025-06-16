package com.springbootweb.spring.boot.web.repositories;

import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity , Long> {
}
