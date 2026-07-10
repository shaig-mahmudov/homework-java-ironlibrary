package com.ironhack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateAuthorRequest(
        @NotBlank(message = "Author name is required")
        String name,
        @NotBlank(message = "Author email is required")
        @Email(message = "Author email must be valid")
        String email
) {}
