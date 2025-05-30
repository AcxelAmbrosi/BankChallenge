package com.challenge.bankAccount;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

@SpringBootApplication
public class BankAccountApplication {

    public static void main(String[] args) {

        SpringApplication.run(BankAccountApplication.class, args);
    }
}
