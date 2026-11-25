package com.company.users_management.application.port.in;

import com.company.users_management.application.services.dto.command.UpdateUserRolCommand;
import com.company.users_management.domain.model.UserModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateUserRolUseCase {
    UserModel updateUserRol(@NotNull @Valid UpdateUserRolCommand command);
}
