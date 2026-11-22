package com.company.users_management.domain.model;

import com.company.users_management.domain.enums.UserRol;
import com.company.users_management.domain.enums.UserStatus;
import com.company.users_management.domain.valueobject.UserEmail;
import com.company.users_management.domain.valueobject.UserId;
import com.company.users_management.domain.valueobject.UserName;
import com.company.users_management.domain.valueobject.UserPassword;

public record UserModel(
        UserId id,
        UserName name,
        UserEmail email,
        UserPassword password,
        UserRol rol,
        UserStatus status
) {

    public static UserModel create(
            final UserId id,
            final UserName name,
            final UserEmail email,
            final UserPassword password,
            final UserRol rol
    ) {
        return new UserModel(id, name, email, password, rol, UserStatus.PENDING);
    }

}
