package com.company.users_management.application.services.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserNameCommand(
        @NotBlank(message = "Id cannot be blank")
        @Size(min = 1, max = 36)
        String id,
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 4, max = 30, message = "Name must be between 3 and 30 characters")
        String name
) {

}
