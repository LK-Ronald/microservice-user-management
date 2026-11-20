package com.company.users_management.domain.enums;

import com.company.users_management.domain.exception.InvalidUserRolException;

import java.util.regex.Pattern;

public enum UserRol {
    ADMIN,
    USER,
    REVIEWER;

    private static final Pattern ASCII_LETTERS = Pattern.compile("[A-Za-z]+");

    public static UserRol fromString(final String value) {
        validateNotNullOrNotBlak(value);
        validateAsciiLetters(value);
        for (final UserRol userRol : values()) {
            if (userRol.name().equalsIgnoreCase(value)) {
                return userRol;
            }
        }
        throw InvalidUserRolException.becauseInvalidValue();
    }

    private static void validateNotNullOrNotBlak(final String value) {
        if (value == null || value.isBlank()) {
            throw InvalidUserRolException.becauseValueIsBlank();
        }
    }

    private static void validateAsciiLetters(final String value) {
        if (!ASCII_LETTERS.matcher(value).matches()) {
            throw InvalidUserRolException.becauseInvalidValue();
        }
    }
}
