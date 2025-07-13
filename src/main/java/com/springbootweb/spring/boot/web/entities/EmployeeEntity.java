package com.springbootweb.spring.boot.web.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springbootweb.spring.boot.web.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "employees")


public class EmployeeEntity extends  AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long employeeid;

    private String name;
    private String email;
    private int age;
    @JsonProperty(value = "isactive")
    private boolean isactive;
    private LocalDate joining;

    @OneToOne(mappedBy = "manager")
    @JsonIgnore
    private DeptEntity managedDepartment;

    @ManyToOne
    @JoinColumn( name = "Worker_dept_id" , referencedColumnName = "departmentid")
    private  DeptEntity workerdepartment;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SalaryEntity> salaries;

    // OneToMany relationship with Attendance
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttendanceEntity> attendances;


    @Column(unique = true , nullable = false)
    private  String username;

    private  String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("ROLE_" + this.roles))
                .stream().collect(Collectors.toSet());

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
