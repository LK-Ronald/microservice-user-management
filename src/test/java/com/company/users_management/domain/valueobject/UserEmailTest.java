package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserEmailTest {

    @Test
    void normalizesEmailAndComparesByValue() {
        UserEmail email = new UserEmail("  USER+TAG@EXAMPLE.COM  ");

        assertEquals("user+tag@example.com", email.value());
        assertEquals(new UserEmail("user+tag@example.com"), email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan perez@ejemplo.com", "juan@perez@ejemplo.com", "juan(ventas)@ejemplo.com",
            "juan[2026]@ejemplo.com", "juan<admin>@ejemplo.com", "juan,perez@ejemplo.com", "juan;perez@ejemplo.com",
            "juan:perez@ejemplo.com", "juan\"perez\"@ejemplo.com", "juan\\perez@ejemplo.com", "juan/perez@ejemplo.com",
            "juan#1@ejemplo.com", "juan$perez@ejemplo.com", "juan%perez@ejemplo.com", "juan&maria@ejemplo.com", "juan*perez@ejemplo.com",
            "juan=perez@ejemplo.com", "juan?@ejemplo.com", "josé.muñoz@ejemplo.com", ".juanperez@ejemplo.com", "juanperez.@ejemplo.com",
            "juan..perez@ejemplo.com", "-juanperez@ejemplo.com", "juanperez@-ejemplo.com", "juanperez@ejemplo_dominio.com"
    })
    void rejectsEmailsOutsideApplicationFormat(String input) {
        assertThrows(InvalidUserEmailException.class, () -> new UserEmail(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan.perez@ejemplo.com", "juan-perez@mi-ejemplo.com",
            "juan_perez+ventas@mail.ejemplo.com"})
    void acceptsValidPunctuationAndSubdomains(String input) {
        assertEquals(input, new UserEmail(input).value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan@ejemplo-.com", "juan@ejemplo..com",
            "juan@ventas.-ejemplo.com", "juan@.ejemplo.com"})
    void rejectsInvalidDomainLabels(String input) {
        assertThrows(InvalidUserEmailException.class, () -> new UserEmail(input));
    }

    @Test
    void acceptsLengthBoundaries() {
        String maximum = "a".repeat(250) + "@b.co";

        assertEquals("a@b.co", new UserEmail(" a@b.co ").value());
        assertEquals(maximum, new UserEmail(maximum).value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "a@b.c", "missing-at.example.com", "a@@b.com", "a b@example.com", "a@b.com\r\nBcc:other@example.com"})
    void rejectsInvalidEmail(String input) {
        assertThrows(InvalidUserEmailException.class, () -> new UserEmail(input));
    }

    @Test
    void rejectsEmailAboveMaximumLength() {
        String input = "a".repeat(251) + "@b.co";

        assertThrows(InvalidUserEmailException.class, () -> new UserEmail(input));
    }

    @Test
    void rejectsNull() {
        assertThrows(NullPointerException.class, () -> new UserEmail(null));
    }
}
