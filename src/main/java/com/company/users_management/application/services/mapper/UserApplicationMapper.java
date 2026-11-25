package com.company.users_management.application.services.mapper;

import com.company.users_management.application.services.dto.command.CreateUserCommand;
import com.company.users_management.application.services.dto.command.DeleteUserCommand;
import com.company.users_management.application.services.dto.query.GetUserByIdQuery;
import com.company.users_management.domain.enums.UserRol;
import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserEmail;
import com.company.users_management.domain.valueobject.UserId;
import com.company.users_management.domain.valueobject.UserName;
import com.company.users_management.domain.valueobject.UserPassword;

public final class UserApplicationMapper {
    private UserApplicationMapper() {
    }

    public static UserModel fromCreateCommandToModel(final CreateUserCommand command) {
        return UserModel.create(
                new UserId(command.id()),
                new UserName(command.name()),
                new UserEmail(command.email()),
                UserPassword.fromTextPlain(command.password()),
                UserRol.fromString(command.rol())
        );
    }

    public static UserId fromGetByIdCommandToUserId(final GetUserByIdQuery command) {
        return new UserId(command.id());
    }

    public static UserId fromDeleteCommandToUserId(final DeleteUserCommand command) {
        return new UserId(command.id());
    }
}
