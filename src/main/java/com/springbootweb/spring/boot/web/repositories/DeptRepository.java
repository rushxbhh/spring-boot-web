package com.springbootweb.spring.boot.web.repositories;


import com.springbootweb.spring.boot.web.entities.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<DeptEntity , Long> {
}
