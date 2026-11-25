package com.company.users_management.application.port.out;

import com.company.users_management.domain.enums.UserRol;
import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserId;

public interface UpdateUserRolPort {
    UserModel updateUserRol(UserId id, UserRol rol);
}
