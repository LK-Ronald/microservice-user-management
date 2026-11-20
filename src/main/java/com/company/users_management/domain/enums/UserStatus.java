package com.company.users_management.domain.enums;

import com.company.users_management.domain.exception.InvalidUserStatusException;

import java.util.regex.Pattern;

public enum UserStatus {
    ACTIVE,
    PENDING,
    BLOCKED,
    INACTIVE;

    private static final Pattern ASCII_LETTERS = Pattern.compile("[A-Za-z]+");

    public static UserStatus fromString(final String value) {
        validateNotNullOrBlak(value);
        validateAsciiLetters(value);
        for (final UserStatus userStatus : values()) {
            if (userStatus.name().equalsIgnoreCase(value)) {
                return userStatus;
            }
        }
        throw InvalidUserStatusException.becauseInvalidValue();
    }

    private static void validateNotNullOrBlak(final String value) {
        if (value == null || value.isBlank()) {
            throw InvalidUserStatusException.becauseValueIsBlank();
        }
    }

    private static void validateAsciiLetters(final String value) {
        if (!ASCII_LETTERS.matcher(value).matches()) {
            throw InvalidUserStatusException.becauseInvalidValue();
        }
    }
}
