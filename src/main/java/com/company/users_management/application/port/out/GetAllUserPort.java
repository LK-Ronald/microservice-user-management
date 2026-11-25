package com.company.users_management.application.port.out;

import com.company.users_management.domain.model.UserModel;

import java.util.List;

public interface GetAllUserPort {
    List<UserModel> getAllUsers();
}
