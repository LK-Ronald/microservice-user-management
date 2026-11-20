package com.company.users_management.domain.exception;

public class InvalidUserIdException extends DomainException {
    private static final String MESSAGE_BLAK = "User id cannot be blank";

    private InvalidUserIdException(String message) {
        super(message);
    }

    public static InvalidUserIdException becauseValueIsBlank() {
        return new InvalidUserIdException(MESSAGE_BLAK);
    }

    public static InvalidUserIdException becauseLengthIsTooLong(final String value) {
        return new InvalidUserIdException(String.format("User id must be at most %d characters long", value.length()));
    }
}
