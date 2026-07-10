package com.ironhack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IssueBookRequest(
        @NotNull(message = "Student ID is required")
        UUID studentId,
        @NotNull(message = "Book ID is required")
        UUID bookId,
        @NotBlank(message = "Issue date is required")
        String issueDate,
        @NotBlank(message = "Return date is required")
        String returnDate
) {}
