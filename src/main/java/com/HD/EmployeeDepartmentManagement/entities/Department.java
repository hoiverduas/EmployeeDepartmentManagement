package com.HD.EmployeeDepartmentManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "departments" )
@Data
@AllArgsConstructor

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departmentCode;
    private String departmentName;
    private LocalDateTime creationTimestamp;
    private LocalDateTime modificationTimestamp;
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    List<Employee> employees;

    public Department() {
        setDepartmentCode(UUID.randomUUID().toString());
    }
}
