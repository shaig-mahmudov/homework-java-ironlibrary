package com.ironhack.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateStudentRequest(
        @NotBlank(message = "Student usn is required")
        String usn,
        @NotBlank(message = "Student name is required")
        String name
) {
}
