package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserEmailException;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record UserEmail(String value) {

    private static final String LOCAL_PART = "(?!-)[a-z0-9_+-]+(?:\\.[a-z0-9_+-]+)*";
    private static final String DOMAIN_LABEL = "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(LOCAL_PART + "@(?:" + DOMAIN_LABEL + "\\.)+[a-z]{2,}");
    private static final int MAX_LENGTH = 255;
    private static final int MIN_LENGTH = 6;

    public UserEmail {
        final String normalizedValue = Objects.requireNonNull(value, "User email cannot be null")
                .trim().toLowerCase(Locale.ROOT);
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
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw InvalidUserEmailException.becauseInvalidFormat();
        }
    }
}
