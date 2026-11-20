package com.company.users_management.domain.valueobject;

import com.company.users_management.domain.exception.InvalidUserIdException;

import java.util.Objects;

public record UserId(String value) {

    private static final int MAX_LENGTH = 36;

    public UserId {
        final String normalizedValue = Objects.requireNonNull(value, "User id cannot be null").trim();
        validatedNotBlak(normalizedValue);
        validatedMaxLength(normalizedValue);
        value = normalizedValue;
    }

    public void validatedNotBlak(final String value){
        if(value.isBlank()){
            throw InvalidUserIdException.becauseValueIsBlank();
        }
    }

    public void validatedMaxLength(final String value){
        if(value.length() > MAX_LENGTH){
            throw InvalidUserIdException.becauseValueIsBlank();
        }
    }

}
