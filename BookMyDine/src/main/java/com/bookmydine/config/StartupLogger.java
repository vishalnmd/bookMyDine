package com.bookmydine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {

    @Value("${server.port:8080}")
    private String port;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Override
    public void run(String... args) {

        System.out.println("\n====================================");
        System.out.println("🚀 BookMyDine Started Successfully");
        System.out.println("====================================");

        System.out.println("Environment : " + activeProfile);
        System.out.println("Server Port : " + port);
        System.out.println("Database URL: " + dbUrl);
        System.out.println("Database User: " + dbUsername);

        System.out.println("====================================\n");
    }
}
