package com.company.users_management.application.port.out;

import com.company.users_management.domain.model.UserModel;

public interface CreateUserPort {
    UserModel createUser(UserModel user);
}
