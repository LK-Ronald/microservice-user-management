package com.company.users_management.application.services;

import com.company.users_management.application.port.in.UpdateUserPasswordUseCase;
import com.company.users_management.application.port.out.GetUserByIdPort;
import com.company.users_management.application.port.out.UpdatePasswordPort;
import com.company.users_management.application.services.dto.command.UpdateUserPasswordCommand;
import com.company.users_management.domain.exception.UserNotFoundException;
import com.company.users_management.domain.valueobject.UserId;
import com.company.users_management.domain.valueobject.UserPassword;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateUserPasswordService implements UpdateUserPasswordUseCase {
    private final Validator validator;
    private final GetUserByIdPort getUserByIdPort;
    private final UpdatePasswordPort updatePasswordPort;

    @Override
    public void updateUserPassword(UpdateUserPasswordCommand command) {
        validateUpdateUserPasswordCommand(command);

        final UserId id = new UserId(command.id());

        validateUserExist(id);

        final UserPassword password = UserPassword.fromTextPlain(command.password());

        updatePasswordPort.updatePassword(id, password);
    }

    private void validateUpdateUserPasswordCommand(UpdateUserPasswordCommand command) {
        final Set<ConstraintViolation<UpdateUserPasswordCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void validateUserExist(final UserId id) {
        getUserByIdPort.getUserById(id)
                .orElseThrow(() -> UserNotFoundException.becauseUserNotFound(id.value()));
    }
}
