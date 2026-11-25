package com.company.users_management.application.port.out;

import com.company.users_management.domain.valueobject.UserId;
import com.company.users_management.domain.valueobject.UserPassword;

public interface UpdatePasswordPort {
    void updatePassword(UserId id, UserPassword password);
}
