package com.challenge.bankAccount.infrastructure.adapters.input.web.controllers;

import com.challenge.bankAccount.domain.ports.input.ReportServicePort;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.report.ReportEntryResponse;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportServicePort reportServicePort;
    private final ReportMapper mapper;

    @GetMapping("/{client-id}")
    public Flux<ReportEntryResponse> generateAccountStatement(
            @PathVariable("client-id") Long clientId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return reportServicePort.getReport(clientId, startDate, endDate)
                .map(mapper::toDto);
    }
}