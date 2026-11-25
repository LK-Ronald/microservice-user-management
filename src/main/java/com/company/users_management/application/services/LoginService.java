package com.company.users_management.application.services;

import com.company.users_management.application.port.in.LoginUseCase;
import com.company.users_management.application.port.out.GetUserByEmailPort;
import com.company.users_management.application.services.dto.command.LoginCommand;
import com.company.users_management.domain.enums.UserStatus;
import com.company.users_management.domain.exception.InvalidCredentialsException;
import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class LoginService implements LoginUseCase {
    private final Validator validator;
    private final GetUserByEmailPort getUserByEmailPort;

    @Override
    public UserModel login(LoginCommand command) {
        validateLoginCommand(command);

        final UserEmail email = new UserEmail(command.email());
        final UserModel user = findUserByEmail(email);

        verifyPassword(command.password(), user);
        verifyUserStatus(user);

        return user;
    }

    private static void verifyPassword(final String password, final UserModel user) {
        if (!user.password().verifyPlain(password)) {
            throw InvalidCredentialsException.becauseInvalidCredentials();
        }
    }

    private void verifyUserStatus(final UserModel user) {
        if (user.status() != UserStatus.ACTIVE) {
            throw InvalidCredentialsException.becauseInvalidCredentials();
        }
    }

    private UserModel findUserByEmail(UserEmail email) {
        return getUserByEmailPort.getUserByEmail(email).orElseThrow(InvalidCredentialsException::becauseInvalidCredentials);
    }

    private void validateLoginCommand(LoginCommand command) {
        Set<ConstraintViolation<LoginCommand>> validates = validator.validate(command);
        if (!validates.isEmpty()) {
            throw new ConstraintViolationException(validates);
        }
    }
}
