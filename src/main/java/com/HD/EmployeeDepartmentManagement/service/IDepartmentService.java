package com.HD.EmployeeDepartmentManagement.service;

import com.HD.EmployeeDepartmentManagement.dto.DepartmentRequestDTO;
import com.HD.EmployeeDepartmentManagement.dto.DepartmentRequestUpdateDTO;
import com.HD.EmployeeDepartmentManagement.dto.DepartmentResponseDTO;
import com.HD.EmployeeDepartmentManagement.entities.Department;
import com.HD.EmployeeDepartmentManagement.entities.Employee;

import java.util.List;

public interface IDepartmentService {

    DepartmentResponseDTO save(DepartmentRequestDTO departmentRequestDTO);
    List<DepartmentResponseDTO> findAll();
    DepartmentResponseDTO findById(Long id);
    DepartmentResponseDTO update(DepartmentRequestUpdateDTO departmentRequestUpdateDTO);
    void deleteById(Long id);

}
