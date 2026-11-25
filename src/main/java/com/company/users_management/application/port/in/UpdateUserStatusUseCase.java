package com.company.users_management.application.port.in;

import com.company.users_management.application.services.dto.command.UpdateUserStatusCommand;
import com.company.users_management.domain.model.UserModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateUserStatusUseCase {
    UserModel updateUserStatus(@NotNull @Valid UpdateUserStatusCommand command);
}
