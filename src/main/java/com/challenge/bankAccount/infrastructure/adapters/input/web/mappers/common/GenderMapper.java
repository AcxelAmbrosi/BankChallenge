package com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.common;

import com.challenge.bankAccount.domain.enums.Gender;
import org.mapstruct.Named;

public class GenderMapper {
    @Named("stringToGender")
    public static Gender stringToGender(String gender) {
        if (gender == null) return null;
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid gender value: " + gender);
        }
    }

    @Named("genderToString")
    public static String genderToString(Gender gender) {
        return gender != null ? gender.name() : null;
    }
}
