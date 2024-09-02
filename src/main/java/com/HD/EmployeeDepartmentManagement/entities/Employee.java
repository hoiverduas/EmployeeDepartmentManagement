package com.HD.EmployeeDepartmentManagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;
    private String city;
    private String address;
    @Column(name = "email", unique = true)
    private String email;
    private String phone;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
}
