package com.company.users_management.application.services;

import com.company.users_management.application.port.in.DeleteUserUseCase;
import com.company.users_management.application.port.out.DeleteUserPort;
import com.company.users_management.application.port.out.GetUserByIdPort;
import com.company.users_management.application.services.dto.command.DeleteUserCommand;
import com.company.users_management.application.services.dto.query.GetUserByIdQuery;
import com.company.users_management.application.services.mapper.UserApplicationMapper;
import com.company.users_management.domain.exception.UserNotFoundException;
import com.company.users_management.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {
    private final Validator validator;
    private final GetUserByIdPort getUserByIdPort;
    private final DeleteUserPort deleteUserPort;

    @Override
    public void deleteUser(DeleteUserCommand id) {
        validateDeleteUserCommand(id);

        final UserId userId = UserApplicationMapper.fromDeleteCommandToUserId(id);
        ensureUserExists(userId);

        deleteUserPort.deleteUser(userId);
    }

    private void validateDeleteUserCommand(DeleteUserCommand id) {
        final Set<ConstraintViolation<DeleteUserCommand>> violations = validator.validate(id);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureUserExists(final UserId id) {
        getUserByIdPort.getUserById(id)
                .orElseThrow(() -> UserNotFoundException.becauseUserNotFound(id.value()));
    }

}
