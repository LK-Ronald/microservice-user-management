package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserNameException;

import java.util.Objects;

public record UserName(String value) {
    private static final int MINIMUM_LENGTH = 4;
    private static final int MAX_LENGTH = 30;

    public UserName {
        final String normalizedValue = Objects.requireNonNull(value, "Username cannot be null").trim();
        validateNotBlak(normalizedValue);
        validateMinimumLength(normalizedValue);
        validateMaximumLength(normalizedValue);
        value = normalizedValue;
    }

    private void validateNotBlak(final String value) {
        if (value.isBlank()) {
            throw InvalidUserNameException.becauseValueIsBlank();
        }
    }

    private void validateMinimumLength(final String value) {
        if (value.length() < MINIMUM_LENGTH) {
            throw InvalidUserNameException.becauseLengthIsTooShort(MINIMUM_LENGTH);
        }
    }

    private void validateMaximumLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw InvalidUserNameException.becauseLengthIsTooLong(MAX_LENGTH);
        }
    }
}
