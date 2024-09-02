package com.HD.EmployeeDepartmentManagement.exception;


import com.HD.EmployeeDepartmentManagement.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(NoFoundException.class)
    public String handleNoFoundException(NoFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/employees";
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public String handleDuplicateEntityException(DuplicateEntityException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/departments";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public String handleDuplicateEmailException(DuplicateEmailException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/employees";
    }
}
