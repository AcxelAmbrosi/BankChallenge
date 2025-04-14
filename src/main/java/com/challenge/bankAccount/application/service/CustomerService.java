package com.challenge.bankAccount.application.service;

import com.challenge.bankAccount.application.ports.input.CustomerUseCase;
import com.challenge.bankAccount.application.ports.outputs.CustomerRepository;
import com.challenge.bankAccount.domain.exception.NotFoundException;
import com.challenge.bankAccount.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public Flux<Customer> getAllCustomers() {
        log.info("Getting all customers");
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> getCustomerById(Long id) {
        log.info("Getting customer with id: {}", id);
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Customer not found with id: " + id)));
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        log.info("Creating customer: {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> updateCustomer(Long id, Customer customer) {
        log.info("Updating customer with id: {}", id);
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Customer not found with id: " + id)))
                .flatMap(existingCustomer -> {
                    customer.setId(id);
                    return customerRepository.save(customer);
                });
    }

    @Override
    public Mono<Void> deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Customer not found with id: " + id)))
                .flatMap(customer -> customerRepository.deleteById(id));
    }
}
