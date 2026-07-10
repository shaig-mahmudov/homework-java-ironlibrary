package com.ironhack.dto.response;

import java.util.UUID;

public record StudentResponse(
        UUID id,
        String usn,
        String name
) {}
