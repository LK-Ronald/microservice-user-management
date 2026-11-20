package com.company.users_management.domain.exception;

public class InvalidUserEmailException extends DomainException {
    private static final String MESSAGE_BLAK = "User email cannot be blank";
    private static final String MESSAGE_TOO_LONG = "User email must be at most %d characters long";
    private static final String MESSAGE_TOO_SHORT = "User email must be at least %d characters long";
    private static final String MESSAGE_INVALID_FORMAT = "User email must be a valid email address";

    private InvalidUserEmailException(String message) {
        super(message);
    }

    public static InvalidUserEmailException becauseValueIsBlank() {
        return new InvalidUserEmailException(MESSAGE_BLAK);
    }

    public static InvalidUserEmailException becauseLengthIsTooLong(final int maxLength) {
        return new InvalidUserEmailException(String.format(MESSAGE_TOO_LONG, maxLength));
    }

    public static InvalidUserEmailException becauseLengthIsTooShort(final int minLength) {
        return new InvalidUserEmailException(String.format(MESSAGE_TOO_SHORT, minLength));
    }

    public static InvalidUserEmailException becauseInvalidFormat() {
        return new InvalidUserEmailException(MESSAGE_INVALID_FORMAT);
    }
}
