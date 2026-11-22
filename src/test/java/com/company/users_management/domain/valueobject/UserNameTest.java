package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserNameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNameTest {

    @Test
    void trimsOuterWhitespaceAndPreservesCaseAndInternalSpaces() {
        UserName name = new UserName("  Ronald Perez  ");

        assertEquals("Ronald Perez", name.value());
        assertEquals(new UserName("Ronald Perez"), name);
    }

    @Test
    void acceptsLengthBoundaries() {
        String maximum = "a".repeat(30);

        assertEquals("Abcd", new UserName(" Abcd ").value());
        assertEquals(maximum, new UserName(maximum).value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " \t\n", "\u2003", " abc "})
    void rejectsBlankOrShortName(String input) {
        assertThrows(InvalidUserNameException.class, () -> new UserName(input));
    }

    @Test
    void rejectsNameAboveMaximumLength() {
        assertThrows(InvalidUserNameException.class, () -> new UserName("a".repeat(31)));
    }

    @Test
    void rejectsNull() {
        assertThrows(NullPointerException.class, () -> new UserName(null));
    }
}
