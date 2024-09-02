package com.HD.EmployeeDepartmentManagement.repository;

import com.HD.EmployeeDepartmentManagement.entities.Department;
import com.HD.EmployeeDepartmentManagement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department,Long> {

    boolean existsByDepartmentName(String departmentName);

}
