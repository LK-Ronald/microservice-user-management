package com.company.users_management.domain.exception;

public class UserNotFoundException extends DomainException {
    private static final String MESSAGE_NOT_FOUND = "User with id '%s' not found";

    private UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException becauseUserNotFound(final String userId) {
        return new UserNotFoundException(String.format(MESSAGE_NOT_FOUND, userId));
    }
}
