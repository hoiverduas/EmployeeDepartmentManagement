package com.HD.EmployeeDepartmentManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanelController {

    @GetMapping("/panel")
    public String showPanel() {
        return "panel"; // Nombre del archivo Thymeleaf (panel.html)
    }
}