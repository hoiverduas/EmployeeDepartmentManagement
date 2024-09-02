package com.HD.EmployeeDepartmentManagement.controller;


import com.HD.EmployeeDepartmentManagement.entities.Employee;
import com.HD.EmployeeDepartmentManagement.service.impl.DepartmentService;
import com.HD.EmployeeDepartmentManagement.service.impl.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping
    public String homeEmployee(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "home-employee";
    }

    @GetMapping("/create")
    public String createEmployee(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("employee", new Employee()); // Usando la entidad Employee
        return "create-employee";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee); // Usando la entidad Employee
        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.findById(id); // Usando la entidad Employee
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("documentTypes", new String[]{"RC", "TI", "CC", "CE"});

        return "update-employee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Attempting to delete employee with ID: {}", id);

        try {
            // Verifica si el empleado existe antes de intentar eliminarlo
            Employee employee = employeeService.findById(id);
            if (employee != null) {
                employeeService.deleteById(id);
                logger.info("Employee with ID: {} deleted successfully.", id);
                redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully.");
            } else {
                logger.warn("No employee found with ID: {}", id);
                redirectAttributes.addFlashAttribute("errorMessage", "Employee not found.");
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting employee with ID: {}", id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting employee. Please try again.");
        }

        return "redirect:/employees";
    }
}


