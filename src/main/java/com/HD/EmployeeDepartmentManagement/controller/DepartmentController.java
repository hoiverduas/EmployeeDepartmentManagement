package com.HD.EmployeeDepartmentManagement.controller;


import com.HD.EmployeeDepartmentManagement.dto.DepartmentRequestDTO;
import com.HD.EmployeeDepartmentManagement.dto.DepartmentResponseDTO;
import com.HD.EmployeeDepartmentManagement.entities.Department;
import com.HD.EmployeeDepartmentManagement.service.impl.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping()
    public String home(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "home-department";
    }


    @GetMapping("/create")
    public String createDepartment(Model model) {
        model.addAttribute("department", new DepartmentRequestDTO());
        return "create-department";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute DepartmentRequestDTO departmentRequestDTO) {
        departmentService.save(departmentRequestDTO);
        return "redirect:/departments"; // Redirección después de guardar
    }


    @GetMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable("id") Long id, Model model) {
        DepartmentResponseDTO department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "update-department";
    }


    @GetMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteById(id);
        return "redirect:/departments";
    }


    @GetMapping("/panel")
    public String panel() {
        return "panel";
    }


}
