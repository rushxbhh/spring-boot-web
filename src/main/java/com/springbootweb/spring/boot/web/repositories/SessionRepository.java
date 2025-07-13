package com.springbootweb.spring.boot.web.repositories;

import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUser(EmployeeEntity user);

    Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
