package com.sparta.spartaminiproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpartaminiprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaminiprojectApplication.class, args);
    }

}
