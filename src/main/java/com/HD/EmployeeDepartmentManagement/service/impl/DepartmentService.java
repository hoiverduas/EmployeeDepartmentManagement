package com.HD.EmployeeDepartmentManagement.service.impl;

import com.HD.EmployeeDepartmentManagement.dto.DepartmentRequestDTO;
import com.HD.EmployeeDepartmentManagement.dto.DepartmentRequestUpdateDTO;
import com.HD.EmployeeDepartmentManagement.dto.DepartmentResponseDTO;
import com.HD.EmployeeDepartmentManagement.entities.Department;
import com.HD.EmployeeDepartmentManagement.exception.DuplicateEntityException;
import com.HD.EmployeeDepartmentManagement.exception.NoFoundException;
import com.HD.EmployeeDepartmentManagement.repository.IDepartmentRepository;
import com.HD.EmployeeDepartmentManagement.service.IDepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class DepartmentService implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;
    private final ObjectMapper objectMapper;

    public DepartmentService(IDepartmentRepository departmentRepository, ObjectMapper objectMapper) {
        this.departmentRepository = departmentRepository;
        this.objectMapper = objectMapper;
    }

    private final String MESSAGE = "No se encuentra el departamento ";

    @Override
    public DepartmentResponseDTO save(DepartmentRequestDTO departmentRequestDTO) {
        String departmentName = departmentRequestDTO.getDepartmentName();
        Long departmentId = departmentRequestDTO.getId();


        boolean nameExists = departmentRepository.existsByDepartmentName(departmentName);

        if (departmentId != null) {

            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new NoFoundException("Department not found"));

            if (nameExists && !department.getDepartmentName().equals(departmentName)) {
                throw new DuplicateEntityException("Department name already exists.");
            }

            department.setDepartmentName(departmentName);
            department.setModificationTimestamp(LocalDateTime.now());
            department.setDepartmentCode(UUID.randomUUID().toString());

            departmentRepository.save(department);
            return mapToDto(department);

        } else {

            if (nameExists) {
                throw new DuplicateEntityException("Department name already exists.");
            }

            Department department = mapToEntity(departmentRequestDTO);
            department.setCreationTimestamp(LocalDateTime.now());
            department.setModificationTimestamp(LocalDateTime.now());

            departmentRepository.save(department);
            return mapToDto(department);
        }
    }


    @Override
    public List<DepartmentResponseDTO> findAll() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public DepartmentResponseDTO findById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new NoFoundException(MESSAGE));
        return mapToDto(department);
    }

    @Override
    public DepartmentResponseDTO update(DepartmentRequestUpdateDTO departmentRequestUpdateDTO) {
        findById(departmentRequestUpdateDTO.getId());
        Department department = departmentRepository.save(mapToEntity(departmentRequestUpdateDTO));
        return mapToDto(department);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        departmentRepository.deleteById(id);
    }

    private DepartmentResponseDTO mapToDto(Department department) {
        return objectMapper.convertValue(department, DepartmentResponseDTO.class);
    }

    private Department mapToEntity(DepartmentRequestDTO departmentRequestDTO) {
        Department department = objectMapper.convertValue(departmentRequestDTO, Department.class);
        if (department.getId() == null) {
            department.setDepartmentCode(UUID.randomUUID().toString());
        }
        return department;
    }

    private Department mapToEntity(DepartmentRequestUpdateDTO departmentRequestUpdateDTO) {
        Department department = objectMapper.convertValue(departmentRequestUpdateDTO, Department.class);
        if (department.getId() == null) {
            department.setDepartmentCode(UUID.randomUUID().toString());
        }
        return department;
    }
}
