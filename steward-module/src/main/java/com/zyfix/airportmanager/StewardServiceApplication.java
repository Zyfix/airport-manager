package com.zyfix.airportmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class StewardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StewardServiceApplication.class, args);
    }

}
