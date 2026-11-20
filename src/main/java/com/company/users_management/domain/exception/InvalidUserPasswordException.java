package com.company.users_management.domain.exception;

public class InvalidUserPasswordException extends RuntimeException {
    private static final String MESSAGE_BLAK = "User password cannot be blank";
    private static final String MESSAGE_TOO_SHORT = "User password must be at least %d characters long";
    private static final String MESSAGE_TOO_LONG = "User password must be at most %d characters long";

    private InvalidUserPasswordException(String message) {
        super(message);
    }

    public static InvalidUserPasswordException becauseValueIsBlank() {
        return new InvalidUserPasswordException(MESSAGE_BLAK);
    }

    public static InvalidUserPasswordException becauseLengthIsTooShort(final int minLength) {
        return new InvalidUserPasswordException(String.format(MESSAGE_TOO_SHORT, minLength));
    }

    public static InvalidUserPasswordException becauseLengthIsTooLong(final int maxLength) {
        return new InvalidUserPasswordException(String.format(MESSAGE_TOO_LONG, maxLength));
    }
}
