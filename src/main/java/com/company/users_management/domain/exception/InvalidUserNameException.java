package com.company.users_management.domain.exception;

public class InvalidUserNameException extends DomainException {
    private static final String MESSAGE_BLAK = "Username cannot be blank";
    private static final String MESSAGE_TOO_SHORT = "Username must be at least %d characters long";
    private static final String MESSAGE_TOO_LONG = "Username must be at most %d characters long";

    private InvalidUserNameException(final String message) {
        super(message);
    }

    public static InvalidUserNameException becauseValueIsBlank() {
        return new InvalidUserNameException(MESSAGE_BLAK);
    }

    public static InvalidUserNameException becauseLengthIsTooShort(final int minLength) {
        return new InvalidUserNameException(String.format(MESSAGE_TOO_SHORT, minLength));
    }

    public static InvalidUserNameException becauseLengthIsTooLong(final int maxLength) {
        return new InvalidUserNameException(String.format(MESSAGE_TOO_LONG, maxLength));
    }
}
