package com.challenge.bankAccount.infrastructure.adapters.input.web.controllers;

import com.challenge.bankAccount.domain.ports.input.ReportServicePort;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.report.AccountStatementResponse;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportServicePort reportServicePort;
    private final ReportMapper mapper;

    @GetMapping("/{client-id}")
    public Mono<AccountStatementResponse> generateAccountStatement(
            @PathVariable("client-id") Long clientId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        return reportServicePort.generateAccountStatement(clientId, startDateTime, endDateTime)
                .map(mapper::toResponse)
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()))
                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found or no data for the given criteria.")));
    }
}