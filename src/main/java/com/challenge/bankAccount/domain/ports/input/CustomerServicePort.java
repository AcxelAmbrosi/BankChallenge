package com.challenge.bankAccount.domain.ports.input;

import com.challenge.bankAccount.domain.models.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerServicePort {
    Flux<Customer> getAllCustomers();

    Mono<Customer> getCustomerById(Long id);

    Mono<Customer> createCustomer(Customer customer);

    Mono<Customer> updateCustomer(Long id, Customer customer);

    Mono<Void> deleteCustomer(Long id);
}