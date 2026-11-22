package com.company.users_management.domain.enums;

import com.company.users_management.domain.exception.InvalidUserRolException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserRolTest {

    @ParameterizedTest
    @CsvSource({
            "ADMIN, ADMIN, admin, AdmiN",
            "USER, USER, user, UseR",
            "REVIEWER, REVIEWER, reviewer, RevieweR"
    })
    void parsesEveryValueWithDifferentAsciiCasing(
            UserRol expected, String uppercase, String lowercase, String mixedCase) {
        assertSame(expected, UserRol.fromString(uppercase));
        assertSame(expected, UserRol.fromString(lowercase));
        assertSame(expected, UserRol.fromString(mixedCase));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\r\n", "\u2003"})
    void rejectsMissingOrBlankInputWithDomainException(String input) {
        InvalidUserRolException exception = assertThrows(
                InvalidUserRolException.class, () -> UserRol.fromString(input));

        assertEquals(InvalidUserRolException.becauseValueIsBlank().getMessage(), exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"GUEST", "ACTIVE", "0", "ADMIN,USER", "AD MIN", " ADMIN", "ADMIN\t",
            "ADM\u0130N", "U\u017FER", "\uFF21DMIN"})
    void rejectsUnknownOrMalformedInputWithDomainException(String input) {
        InvalidUserRolException exception = assertThrows(
                InvalidUserRolException.class, () -> UserRol.fromString(input));

        assertEquals(InvalidUserRolException.becauseInvalidValue().getMessage(), exception.getMessage());
    }
}
