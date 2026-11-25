package com.company.users_management.application.port.in;

import com.company.users_management.application.services.dto.command.UpdateUserPasswordCommand;
import com.company.users_management.domain.model.UserModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UpdateUserPasswordUseCase {
    void updateUserPassword(@NotNull @Valid UpdateUserPasswordCommand command);
}
