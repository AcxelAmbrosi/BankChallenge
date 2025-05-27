package com.challenge.bankAccount.infrastructure.adapters.driver.web.mappers.common;

import com.challenge.bankAccount.domain.enums.MovementType;
import org.mapstruct.Named;

public class MovementTypeMapper {
    @Named("stringToMovementType")
    public static MovementType stringToMovementType(String type) {
        if (type == null) return null;
        try {
            return MovementType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid movement type: " + type);
        }
    }

    @Named("movementTypeToString")
    public static String movementTypeToString(MovementType type) {
        return type != null ? type.name() : null;
    }
}
