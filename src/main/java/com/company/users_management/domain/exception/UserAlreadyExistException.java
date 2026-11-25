package com.company.users_management.domain.exception;

public class UserAlreadyExistException extends DomainException {
    private static final String MESSAGE_ALREADY_EXIST = "A user with email '%s' already exists.";

    private UserAlreadyExistException(String message) {
        super(message);
    }

    public static UserAlreadyExistException becauseEmailIsAlreadyUsed(final String email) {
        return new UserAlreadyExistException(String.format(MESSAGE_ALREADY_EXIST, email));
    }

}
