package com.company.users_management.application.port.out;

import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserId;

import java.util.Optional;

public interface GetUserByIdPort {
    Optional<UserModel> getUserById(UserId id);
}
