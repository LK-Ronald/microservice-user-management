package com.company.users_management.domain.exception;

public class InvalidUserRolException extends DomainException {
    private static final String MESSAGE_BLAK = "User rol cannot be blank";
    private static final String MESSAGE_INVALID_VALUE = "User rol is not valid";

    private InvalidUserRolException(String message) {
        super(message);
    }

    public static InvalidUserRolException becauseValueIsBlank() {
        return new InvalidUserRolException(MESSAGE_BLAK);
    }

    public static InvalidUserRolException becauseInvalidValue() {
        return new InvalidUserRolException(MESSAGE_INVALID_VALUE);
    }
}
