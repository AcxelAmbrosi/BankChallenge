package com.challenge.bankAccount.infrastructure.adapters.input.web.controllers;

import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.customer.CustomerCreateDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.customer.CustomerResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.CustomerMapper;
import com.challenge.bankAccount.domain.ports.input.CustomerServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServicePort customerServicePort;
    private final CustomerMapper mapper;

    @GetMapping
    public Flux<CustomerResponseDto> getAllCustomers() {
        log.info("REST request to get all customers");
        return customerServicePort.getAllCustomers()
                .map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        log.info("REST request to get customer by id: {}", id);
        return customerServicePort.getCustomerById(id)
                .map(mapper::toDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerCreateDto createDto) {
        log.info("REST request to create customer: {}", createDto);
        return Mono.just(createDto)
                .map(mapper::toDomain)
                .flatMap(customerServicePort::createCustomer)
                .map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomerResponseDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerCreateDto updateDto) {
        log.info("REST request to update customer with id: {}", id);
        return Mono.just(updateDto)
                .map(mapper::toDomain)
                .flatMap(customer -> customerServicePort.updateCustomer(id, customer))
                .map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        log.info("REST request to delete customer with id: {}", id);
        return customerServicePort.deleteCustomer(id);
    }
}
