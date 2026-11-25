package com.company.users_management.application.services;

import com.company.users_management.application.port.in.CreateUserUseCase;
import com.company.users_management.application.port.out.CreateUserPort;
import com.company.users_management.application.port.out.GetUserByEmailPort;
import com.company.users_management.application.services.dto.command.CreateUserCommand;
import com.company.users_management.application.services.mapper.UserApplicationMapper;
import com.company.users_management.domain.exception.UserAlreadyExistException;
import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {
    private final Validator validator;
    private final GetUserByEmailPort getUserByEmailPort;
    private final CreateUserPort createUserPort;

    @Override
    public UserModel createUser(final CreateUserCommand command) {
        validateCreateUserCommand(command);

        final UserEmail email = new UserEmail(command.email());
        ensureEmailIsNotTaken(email);

        final UserModel user = UserApplicationMapper.fromCreateCommandToModel(command);
        createUserPort.createUser(user);

        return user;
    }

    private void validateCreateUserCommand(final CreateUserCommand command) {
        final Set<ConstraintViolation<CreateUserCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureEmailIsNotTaken(final UserEmail email) {
        getUserByEmailPort.getUserByEmail(email).ifPresent(user -> {
            throw UserAlreadyExistException.becauseEmailIsAlreadyUsed(email.value());
        });
    }
}
