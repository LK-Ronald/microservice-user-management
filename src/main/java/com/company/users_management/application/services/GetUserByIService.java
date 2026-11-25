package com.company.users_management.application.services;

import com.company.users_management.application.port.in.GetUserByIdUseCase;
import com.company.users_management.application.port.out.GetUserByIdPort;
import com.company.users_management.application.services.dto.query.GetUserByIdQuery;
import com.company.users_management.application.services.mapper.UserApplicationMapper;
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
public class GetUserByIService implements GetUserByIdUseCase {
    private final Validator validator;
    private final GetUserByIdPort getUserByIdPort;

    @Override
    public UserModel getUserById(GetUserByIdQuery query) {
        validateGetUserByIdQuery(query);

        final UserId id = UserApplicationMapper.fromGetByIdCommandToUserId(query);

        return getUserByIdPort.getUserById(id).orElseThrow(
                () -> UserNotFoundException.becauseUserNotFound(id.value())
        );
    }

    private void validateGetUserByIdQuery(GetUserByIdQuery query) {
        final Set<ConstraintViolation<GetUserByIdQuery>> validates = validator.validate(query);
        if (!validates.isEmpty()) {
            throw new ConstraintViolationException(validates);
        }
    }
}
