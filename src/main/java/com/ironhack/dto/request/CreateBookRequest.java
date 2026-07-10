package com.ironhack.dto.request;

import com.ironhack.model.BookCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateBookRequest(
        @NotBlank(message = "ISBN is required") 
        String isbn,
        @NotBlank(message = "Title is required") 
        String title,
        @NotNull(message = "Category is required")
        BookCategory category,
        @NotNull @Min(value = 1, message = "Quantity must be at least 1") 
        Integer quantity,
        @NotNull(message = "Author ID is required")
        UUID authorId
) {}
