package com.company.users_management.application.services.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordCommand(
        @NotBlank(message = "Id cannot be blank")
        @Size(min = 1, max = 36)
        String id,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
