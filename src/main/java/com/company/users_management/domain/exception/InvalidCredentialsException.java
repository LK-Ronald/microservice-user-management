package com.company.users_management.domain.exception;

public class InvalidCredentialsException extends DomainException {
    private static final String MESSAGE = "Invalid credentials";

    private InvalidCredentialsException(String message) {
        super(message);
    }

    public static InvalidCredentialsException becauseInvalidCredentials() {
        return new InvalidCredentialsException(MESSAGE);
    }
}
