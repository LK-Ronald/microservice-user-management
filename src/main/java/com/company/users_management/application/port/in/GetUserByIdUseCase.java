package com.company.users_management.application.port.in;

import com.company.users_management.application.services.dto.query.GetUserByIdQuery;
import com.company.users_management.domain.model.UserModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetUserByIdUseCase {
    UserModel getUserById(@NotNull @Valid GetUserByIdQuery id);
}
