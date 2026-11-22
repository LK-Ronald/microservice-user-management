package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserIdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserIdTest {

    @Test
    void trimsOuterWhitespaceAndComparesByValue() {
        UserId id = new UserId("  Abc-123  ");

        assertEquals("Abc-123", id.value());
        assertEquals(new UserId("Abc-123"), id);
    }

    @Test
    void acceptsLengthBoundaries() {
        String maximum = "550e8400-e29b-41d4-a716-446655440000";

        assertEquals("a", new UserId("a").value());
        assertEquals(maximum, new UserId(" " + maximum + " ").value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " \t\n", "\u2003"})
    void rejectsBlankId(String input) {
        assertThrows(InvalidUserIdException.class, () -> new UserId(input));
    }

    @Test
    void rejectsIdAboveMaximumLength() {
        assertThrows(InvalidUserIdException.class, () -> new UserId("a".repeat(37)));
    }

    @Test
    void rejectsNull() {
        assertThrows(NullPointerException.class, () -> new UserId(null));
    }
}
