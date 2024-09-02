package com.HD.EmployeeDepartmentManagement.service;

import com.HD.EmployeeDepartmentManagement.dto.*;
import com.HD.EmployeeDepartmentManagement.entities.Department;
import com.HD.EmployeeDepartmentManagement.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    Employee save(Employee employee);
    List<Employee> findAll();
    Employee findById(Long id);
    Employee update(Employee employee);
    void deleteById(Long id);
}
