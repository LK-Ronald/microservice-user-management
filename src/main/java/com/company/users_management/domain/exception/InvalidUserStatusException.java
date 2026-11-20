package com.company.users_management.domain.exception;

public class InvalidUserStatusException extends DomainException {
    private static final String MESSAGE_BLAK = "User status cannot be blank";
    private static final String MESSAGE_INVALID_VALUE = "User status is not valid";

    private InvalidUserStatusException(String message) {
        super(message);
    }

    public static InvalidUserStatusException becauseValueIsBlank() {
        return new InvalidUserStatusException(MESSAGE_BLAK);
    }

    public static InvalidUserStatusException becauseInvalidValue() {
        return new InvalidUserStatusException(MESSAGE_INVALID_VALUE);
    }

}
