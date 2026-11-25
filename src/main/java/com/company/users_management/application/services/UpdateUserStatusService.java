package com.company.users_management.application.services;

import com.company.users_management.application.port.in.UpdateUserStatusUseCase;
import com.company.users_management.application.port.out.GetUserByIdPort;
import com.company.users_management.application.port.out.UpdateUserStatusPort;
import com.company.users_management.application.services.dto.command.UpdateUserStatusCommand;
import com.company.users_management.domain.enums.UserStatus;
import com.company.users_management.domain.exception.UserNotFoundException;
import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateUserStatusService implements UpdateUserStatusUseCase {
    private final Validator validator;
    private final GetUserByIdPort getUserByIdPort;
    private final UpdateUserStatusPort updateStatusPort;

    @Override
    public UserModel updateUserStatus(UpdateUserStatusCommand command) {
        validateUpdateUserStatusCommand(command);

        final UserId id = new UserId(command.id());
        validateUserExist(id);

        final UserStatus status = UserStatus.fromString(command.status());
        return updateStatusPort.updateUserStatus(id, status);
    }

    private void validateUpdateUserStatusCommand(UpdateUserStatusCommand command) {
        final Set<ConstraintViolation<UpdateUserStatusCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void validateUserExist(final UserId id) {
        getUserByIdPort.getUserById(id)
                .orElseThrow(() -> UserNotFoundException.becauseUserNotFound(id.value()));
    }
}
