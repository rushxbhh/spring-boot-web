package com.springbootweb.spring.boot.web.entities;

import jakarta.persistence.*;

import lombok.Getter;

import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "attendances")

public class AttendanceEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "attendance_date" , nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private Boolean isPresent;

    @Column
    private LocalTime checkInTime;

    @Column
    private LocalTime checkOutTime;

    @Column
    private String remarks;

//   @Column(nullable = false)
//  @CreationTimestamp
//    private LocalDateTime createdAt;

    // ManyToOne relationship with Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

}

