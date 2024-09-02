package com.HD.EmployeeDepartmentManagement.repository;

import com.HD.EmployeeDepartmentManagement.entities.Department;
import com.HD.EmployeeDepartmentManagement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);

}
