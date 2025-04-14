package com.challenge.bankAccount.application.ports.outputs;

import com.challenge.bankAccount.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
    Flux<Customer> findAll();

    Mono<Customer> findById(Long id);

    Mono<Customer> save(Customer customer);

    Mono<Void> deleteById(Long id);
}