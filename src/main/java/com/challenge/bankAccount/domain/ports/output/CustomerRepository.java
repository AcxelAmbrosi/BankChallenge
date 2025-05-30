package com.challenge.bankAccount.domain.ports.output;

import com.challenge.bankAccount.domain.models.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
    Flux<Customer> findAll();

    Mono<Customer> findById(Long id);

    Mono<Customer> save(Customer customer);

    Mono<Void> deleteById(Long id);

    Mono<Customer> findByIdentification(String identification);
}