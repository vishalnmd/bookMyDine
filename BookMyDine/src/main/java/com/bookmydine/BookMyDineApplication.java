package com.bookmydine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyDineApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookMyDineApplication.class, args);
    }

}
