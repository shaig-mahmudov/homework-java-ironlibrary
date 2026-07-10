package com.ironhack.dto.response;

import com.ironhack.model.BookCategory;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String isbn,
        String title,
        BookCategory category,
        Integer quantity,
        AuthorResponse author
) {}
