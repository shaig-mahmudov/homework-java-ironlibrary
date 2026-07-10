package com.ironhack.dto.response;

import java.util.UUID;

public record IssueResponse(
        UUID id,
        String issueDate,
        String returnDate,
        BookResponse book,
        StudentResponse student
) {}
