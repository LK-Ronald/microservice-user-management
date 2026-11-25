package com.company.users_management.application.services.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginCommand(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email is not valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 255)
        String password
) {
}
