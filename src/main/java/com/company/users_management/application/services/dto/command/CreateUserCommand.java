package com.company.users_management.application.services.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserCommand(
        @NotBlank(message = "Id cannot be blank")
        @Size(min = 1, max = 36, message = "Id must be 36 characters long")
        String id,
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 4, max = 30, message = "Name must be between 3 and 30 characters")
        String name,
        @NotBlank(message = "Email cannot be blank")
        @Size(min = 6, max = 255)
        @Email(message = "Email is not valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password,
        @NotBlank(message = "Rol cannot be blank")
        String rol
) {
}
