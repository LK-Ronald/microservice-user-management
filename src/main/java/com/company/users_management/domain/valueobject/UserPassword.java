package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserPasswordException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Objects;

public final class UserPassword {
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 25;
    private static final int SALT_ROUNDS = 12;
    private final String value;

    private UserPassword(String value) {
        this.value = value;
    }

    public static UserPassword fromTextPlain(final String value) {
        final String normalizedValue = Objects.requireNonNull(value, "Password cannot be null").trim();
        validateNotBlak(normalizedValue);
        validateLengthMin(normalizedValue);
        validateLengthMax(normalizedValue);
        final String hash = BCrypt.hashpw(normalizedValue, BCrypt.gensalt(SALT_ROUNDS));
        return new UserPassword(hash);
    }

    public static UserPassword fromHash(final String hash) {
        Objects.requireNonNull(hash, "Password cannot be null");
        return new UserPassword(hash);
    }

    public boolean verifyPlain(final String value) {
        final String normalizedValue = Objects.requireNonNull(value, "Password cannot be null").trim();
        validateNotBlak(normalizedValue);
        final String hashOfPlain = BCrypt.hashpw(normalizedValue, BCrypt.gensalt(SALT_ROUNDS));
        return BCrypt.checkpw(normalizedValue, this.value);
    }

    public String value() {
        return this.value;
    }

    private static void validateNotBlak(final String value) {
        if (value.isBlank()) {
            throw InvalidUserPasswordException.becauseValueIsBlank();
        }
    }

    private static void validateLengthMin(final String value) {
        if (value.length() < MIN_LENGTH) {
            throw InvalidUserPasswordException.becauseLengthIsTooShort(8);
        }
    }

    private static void validateLengthMax(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw InvalidUserPasswordException.becauseLengthIsTooLong(25);
        }
    }
}
