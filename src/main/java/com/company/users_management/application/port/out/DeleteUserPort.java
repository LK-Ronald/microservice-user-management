package com.company.users_management.application.port.out;

import com.company.users_management.domain.valueobject.UserId;

public interface DeleteUserPort {
    void deleteUser(UserId id);
}
