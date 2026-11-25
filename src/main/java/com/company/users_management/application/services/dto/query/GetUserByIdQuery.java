package com.company.users_management.application.services.dto.query;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GetUserByIdQuery(
        @NotBlank(message = "Id cannot be blank")
        @Size(min = 1, max = 36)
        String id
) {
}
