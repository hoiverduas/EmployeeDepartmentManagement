package com.HD.EmployeeDepartmentManagement.service.impl;

import com.HD.EmployeeDepartmentManagement.dto.*;
import com.HD.EmployeeDepartmentManagement.entities.Department;
import com.HD.EmployeeDepartmentManagement.entities.Employee;
import com.HD.EmployeeDepartmentManagement.exception.DuplicateEmailException;
import com.HD.EmployeeDepartmentManagement.exception.NoFoundException;
import com.HD.EmployeeDepartmentManagement.repository.IDepartmentRepository;
import com.HD.EmployeeDepartmentManagement.repository.IEmployeeRepository;
import com.HD.EmployeeDepartmentManagement.service.IEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final IDepartmentRepository departmentRepository;

    public EmployeeService(IEmployeeRepository employeeRepository, IDepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Override
    public Employee save(Employee employee) {
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new NoFoundException("Department not found"));

        employee.setDepartment(department);

        Optional<Employee> existingEmployeeWithSameEmail = employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployeeWithSameEmail.isPresent() &&
                (employee.getId() == null || !existingEmployeeWithSameEmail.get().getId().equals(employee.getId()))) {
            throw new DuplicateEmailException("An employee with this email already exists.");
        }

        if (employee.getId() != null) {
            Employee existingEmployee = employeeRepository.findById(employee.getId())
                    .orElseThrow(() -> new NoFoundException("Employee not found"));

            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setDocumentType(employee.getDocumentType());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setDepartment(department);
            existingEmployee.setModifiedDateTime(LocalDateTime.now());

            return employeeRepository.save(existingEmployee);
        } else {
            employee.setCreatedDateTime(LocalDateTime.now());
            employee.setModifiedDateTime(LocalDateTime.now());
            return employeeRepository.save(employee);
        }
    }



    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoFoundException("Employee not found"));
    }

    @Override
    public Employee update(Employee employee) {
        // Verificar que el empleado exista
        Employee existingEmployee = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new NoFoundException("Employee not found"));

        // Asegurarse de que el departamento exista
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new NoFoundException("Department not found"));

        // Actualizar los campos del empleado existente
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setDocumentType(employee.getDocumentType());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartment(department);
        existingEmployee.setModifiedDateTime(LocalDateTime.now());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        logger.info("Attempting to delete employee with ID: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            logger.info("Employee with ID: {} deleted from the repository.", id);
        } else {
            logger.warn("No employee found with ID: {}", id);
        }
    }
}

