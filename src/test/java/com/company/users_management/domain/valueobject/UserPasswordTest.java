package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserPasswordTest {

    @Test
    void storesSaltedBcryptHashesWithExpectedCost() {
        String plain = "Local-Test-123!";
        UserPassword first = UserPassword.fromTextPlain(plain);
        UserPassword second = UserPassword.fromTextPlain(plain);

        assertNotEquals(plain, first.value());
        assertNotEquals(first.value(), second.value());
        assertTrue(first.value().matches("\\$2[aby]?\\$12\\$[./A-Za-z0-9]{53}"));
        assertTrue(BCrypt.checkpw(plain, first.value()));
        assertTrue(BCrypt.checkpw(plain, second.value()));
    }

    @Test
    void restoresHashAndVerifiesOnlyMatchingPassword() {
        String plain = "Local-Test-123!";
        String hash = BCrypt.hashpw(plain, BCrypt.gensalt(4));
        UserPassword restored = UserPassword.fromHash(hash);

        assertEquals(hash, restored.value());
        assertTrue(restored.verifyPlain(" " + plain + " "));
        assertFalse(restored.verifyPlain("Local-Test-124!"));
        assertFalse(restored.verifyPlain(hash));
        assertFalse(restored.toString().contains(hash));
        assertThrows(InvalidUserPasswordException.class, () -> restored.verifyPlain(" \t"));
        assertThrows(NullPointerException.class, () -> restored.verifyPlain(null));
    }

    @Test
    void acceptsLengthBoundariesAfterTrimming() {
        String minimum = "abc123!X";
        String maximum = "x".repeat(25);

        assertTrue(BCrypt.checkpw(minimum, UserPassword.fromTextPlain(" " + minimum + " ").value()));
        assertTrue(BCrypt.checkpw(maximum, UserPassword.fromTextPlain(maximum).value()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " \t\n", "\u2003", " secret ", "abcdefghijklmnopqrstuvwxyz"})
    void rejectsInvalidPasswordsWithoutExposingTheirContents(String input) {
        InvalidUserPasswordException exception = assertThrows(
                InvalidUserPasswordException.class, () -> UserPassword.fromTextPlain(input));

        if (!input.isBlank()) {
            assertFalse(exception.getMessage().contains(input.trim()));
        }
    }

    @Test
    void rejectsNullInputs() {
        assertThrows(NullPointerException.class, () -> UserPassword.fromTextPlain(null));
        assertThrows(NullPointerException.class, () -> UserPassword.fromHash(null));
    }
}
