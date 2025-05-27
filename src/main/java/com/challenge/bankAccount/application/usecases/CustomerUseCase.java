package com.challenge.bankAccount.application.usecases;

import com.challenge.bankAccount.domain.ports.driver.CustomerServicePort;
import com.challenge.bankAccount.domain.ports.driven.CustomerRepository;
import com.challenge.bankAccount.domain.exceptions.ConflictException;
import com.challenge.bankAccount.domain.exceptions.NotFoundException;
import com.challenge.bankAccount.domain.models.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerUseCase implements CustomerServicePort {

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
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Customer not found with id: {}", id);
                    return Mono.error(new NotFoundException("Customer not found with id: " + id));
                }))
                .doOnSuccess(exist -> log.info("Customer found with ID: {}", id))
                .doOnError(e -> log.error("Error occurred while found customer: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        log.info("Creating customer: {}", customer);

        return customerRepository.findByIdentification(customer.getIdentification())
                .flatMap(existing ->
                {
                    log.warn("Conflict: Customer already exists with identification: {}", customer.getIdentification());
                    return Mono.<Customer>error(new ConflictException("Customer already exists with identification: " + customer.getIdentification()));
                })
                .switchIfEmpty(Mono.defer(() -> customerRepository.save(customer)
                        .doOnSuccess(saved -> log.info("Customer successfully created with ID: {}", saved.getId()))
                        .doOnError(e -> log.error("Error occurred while saving customer: {}", e.getMessage(), e))));
    }

    @Override
    public Mono<Customer> updateCustomer(Long id, Customer customer) {
        log.info("Updating customer with id: {}", id);
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Customer not found with id: {}", id);
                    return Mono.error(new NotFoundException("Customer not found with id: " + id));
                }))
                .flatMap(existingCustomer ->
                        customerRepository.findByIdentification(customer.getIdentification())
                                .flatMap(duplicate -> {
                                    if (!duplicate.getId().equals(id)) {
                                        log.warn("Duplicate identification detected: {}", customer.getIdentification());
                                        return Mono.error(new ConflictException("Identification already used by another customer"));
                                    }
                                    customer.setId(id);
                                    return customerRepository.save(customer)
                                            .doOnSuccess(saved -> log.info("Customer updated successfully with id: {}", saved.getId()));
                                })
                                .switchIfEmpty(
                                        Mono.defer(() -> {
                                            customer.setId(id);
                                            return customerRepository.save(customer)
                                                    .doOnSuccess(saved -> log.info("Customer updated successfully with id: {}", saved.getId()));
                                        })
                                )
                                .doOnError(e -> log.error("Failed to update customer with id: {}. Error: {}", id, e.getMessage(), e))
                );
    }

    @Override
    public Mono<Void> deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.defer(() ->
                {
                    log.warn("Customer not found with id: {}", id);
                    return Mono.error(new NotFoundException("Customer not found with id: " + id));
                }))
                .flatMap(customer -> customerRepository.deleteById(id)
                        .doOnSuccess(deleted -> log.info("Customer deleted successfully with id: {}", id)))
                .doOnError(e -> log.error("Failed to delete customer with id: {}. Error: {}", id, e.getMessage(), e));
    }
}
