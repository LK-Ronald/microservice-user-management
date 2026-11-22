package com.company.users_management.domain.enums;

import com.company.users_management.domain.exception.InvalidUserStatusException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserStatusTest {

    @ParameterizedTest
    @CsvSource({
            "ACTIVE, ACTIVE, active, ActivE",
            "PENDING, PENDING, pending, PendinG",
            "BLOCKED, BLOCKED, blocked, BlockeD",
            "INACTIVE, INACTIVE, inactive, InactivE"
    })
    void parsesEveryValueWithDifferentAsciiCasing(
            UserStatus expected, String uppercase, String lowercase, String mixedCase) {
        assertSame(expected, UserStatus.fromString(uppercase));
        assertSame(expected, UserStatus.fromString(lowercase));
        assertSame(expected, UserStatus.fromString(mixedCase));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\r\n", "\u2003"})
    void rejectsMissingOrBlankInputWithDomainException(String input) {
        InvalidUserStatusException exception = assertThrows(
                InvalidUserStatusException.class, () -> UserStatus.fromString(input));

        assertEquals(InvalidUserStatusException.becauseValueIsBlank().getMessage(), exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"DELETED", "ADMIN", "1", "ACTIVE,PENDING", "IN ACTIVE", " ACTIVE", "ACTIVE\n",
            "PEND\u0130NG", "ACT\u0131VE", "\uFF21CTIVE"})
    void rejectsUnknownOrMalformedInputWithDomainException(String input) {
        InvalidUserStatusException exception = assertThrows(
                InvalidUserStatusException.class, () -> UserStatus.fromString(input));

        assertEquals(InvalidUserStatusException.becauseInvalidValue().getMessage(), exception.getMessage());
    }
}
