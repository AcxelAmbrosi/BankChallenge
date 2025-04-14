package com.challenge.bankAccount.application.ports.input;

import com.challenge.bankAccount.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerUseCase {
    Flux<Customer> getAllCustomers();

    Mono<Customer> getCustomerById(Long id);

    Mono<Customer> createCustomer(Customer customer);

    Mono<Customer> updateCustomer(Long id, Customer customer);

    Mono<Void> deleteCustomer(Long id);
}