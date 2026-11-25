package com.company.users_management.application.services;

import com.company.users_management.application.port.in.UpdateUserRolUseCase;
import com.company.users_management.application.port.out.GetUserByIdPort;
import com.company.users_management.application.port.out.UpdateUserRolPort;
import com.company.users_management.application.services.dto.command.UpdateUserRolCommand;
import com.company.users_management.domain.enums.UserRol;
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
public class UpdateUserRolService implements UpdateUserRolUseCase {
    private final Validator validator;
    private final GetUserByIdPort getUserByIdPort;
    private final UpdateUserRolPort updateRolPort;

    @Override
    public UserModel updateUserRol(UpdateUserRolCommand command) {
        validateUpdateUserRolCommand(command);

        final UserId id = new UserId(command.id());

        validateUserExist(id);

        final UserRol rol = UserRol.fromString(command.rol());
        return updateRolPort.updateUserRol(id, rol);
    }

    private void validateUpdateUserRolCommand(UpdateUserRolCommand command) {
        final Set<ConstraintViolation<UpdateUserRolCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void validateUserExist(final UserId id) {
        getUserByIdPort.getUserById(id)
                .orElseThrow(() -> UserNotFoundException.becauseUserNotFound(id.value()));
    }
}
