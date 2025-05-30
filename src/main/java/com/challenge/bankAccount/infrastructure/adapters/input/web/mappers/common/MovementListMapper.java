package com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.common;

import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.movement.MovementResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.MovementMapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MovementListMapper {

    @Autowired
    private MovementMapper movementMapper;

    @Named("mapMovementsByAccountId")
    public Map<Long, List<MovementResponseDto>> mapMovementsByAccountId(Map<Long, List<Movement>> source) {
        if (source == null) return null;

        return source.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(movementMapper::toDto)
                                .collect(Collectors.toList())
                ));
    }
}
