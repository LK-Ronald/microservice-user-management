package com.company.users_management.application.services;

import com.company.users_management.application.port.in.UpdateUserNameUseCase;
import com.company.users_management.application.port.out.GetUserByIdPort;
import com.company.users_management.application.port.out.UpdateUserNamePort;
import com.company.users_management.application.services.dto.command.UpdateUserNameCommand;
import com.company.users_management.application.services.mapper.UserApplicationMapper;
import com.company.users_management.domain.exception.UserNotFoundException;
import com.company.users_management.domain.model.UserModel;
import com.company.users_management.domain.valueobject.UserId;
import com.company.users_management.domain.valueobject.UserName;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UpdateUserNameService implements UpdateUserNameUseCase {
    private final Validator validator;
    private final GetUserByIdPort getUserByIService;
    private final UpdateUserNamePort updateUserNamePort;

    @Override
    public UserModel updateUserName(UpdateUserNameCommand command) {
        validateUpdateUserNameCommand(command);

        final UserId id = new UserId(command.id());
        final UserName name = new UserName(command.name());

        final UserModel user = getUserByIService.getUserById(id).orElseThrow(
                () -> UserNotFoundException.becauseUserNotFound(id.value()));

        return updateUserNamePort.updateUserName(id, name);
    }

    private void validateUpdateUserNameCommand(UpdateUserNameCommand command) {
        final Set<ConstraintViolation<UpdateUserNameCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
