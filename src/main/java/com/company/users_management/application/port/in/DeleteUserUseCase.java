package com.company.users_management.application.port.in;

import com.company.users_management.application.services.dto.command.DeleteUserCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteUserUseCase {
    void deleteUser(@NotNull @Valid DeleteUserCommand id);
}
