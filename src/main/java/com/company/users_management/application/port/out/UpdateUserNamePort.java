package com.company.users_management.application.port.out;

import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserId;
import com.company.users_management.domain.valueobject.UserName;

public interface UpdateUserNamePort {
    UserModel updateUserName(UserId id, UserName name);
}
