package com.company.users_management.application.port.out;

import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserEmail;

import java.util.Optional;

public interface GetUserByEmailPort {
    Optional<UserModel> getUserByEmail(UserEmail email);
}
