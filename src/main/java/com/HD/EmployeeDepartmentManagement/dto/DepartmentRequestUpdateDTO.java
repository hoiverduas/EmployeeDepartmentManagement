package com.HD.EmployeeDepartmentManagement.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DepartmentRequestUpdateDTO {

    private Long id;
    private String departmentCode;
    private String departmentName;
    private LocalDateTime creationTimestamp;
    private LocalDateTime modificationTimestamp;
}
