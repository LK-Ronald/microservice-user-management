package com.company.users_management.application.port.in;

import com.company.users_management.domain.model.UserModel;

import java.util.List;

public interface GetAllUserUseCase {
    List<UserModel> getAllUsers();
}
