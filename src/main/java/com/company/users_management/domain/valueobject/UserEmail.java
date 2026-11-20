package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserEmailException;

import java.util.Objects;

public record UserEmail(String value) {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";
    private static final int MAX_LENGTH = 255;
    private static final int MIN_LENGTH = 6;

    public UserEmail {
        final String normalizedValue = Objects.requireNonNull(value, "User email cannot be null").trim().toLowerCase();
        validatedMinimumLength(normalizedValue);
        validatedMaximumLength(normalizedValue);
        validatedFormat(normalizedValue);
        value = normalizedValue;
    }

    private void validatedMaximumLength(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw InvalidUserEmailException.becauseLengthIsTooLong(MAX_LENGTH);
        }
    }

    private void validatedMinimumLength(final String value) {
        if (value.length() < MIN_LENGTH) {
            throw InvalidUserEmailException.becauseLengthIsTooShort(MIN_LENGTH);
        }
    }

    private void validatedFormat(final String value) {
        if (!value.matches(EMAIL_PATTERN)) {
            throw InvalidUserEmailException.becauseInvalidFormat();
        }
    }
}
