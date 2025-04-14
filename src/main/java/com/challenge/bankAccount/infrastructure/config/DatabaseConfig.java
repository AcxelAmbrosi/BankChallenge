package com.challenge.bankAccount.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.core.DatabaseClient;

import java.nio.charset.StandardCharsets;

@Configuration
public class DatabaseConfig {

    private final DatabaseClient databaseClient;

    @Value("classpath:schema.sql")
    private Resource schemaScript;

    public DatabaseConfig(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @PostConstruct
    public void initializeDatabase() {
        try {
            String script = new String(schemaScript.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            System.out.println("El script SQL es: " + script);

            String[] statements = script.split(";");

            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    System.out.println("Ejecutando SQL: " + trimmed);
                    databaseClient.sql(trimmed).then().subscribe();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar el script SQL", e);
        }
    }
}
