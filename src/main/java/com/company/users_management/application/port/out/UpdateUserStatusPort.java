package com.company.users_management.application.port.out;

import com.company.users_management.domain.enums.UserStatus;
import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserId;

public interface UpdateUserStatusPort {
    UserModel updateUserStatus(UserId id, UserStatus status);
}
