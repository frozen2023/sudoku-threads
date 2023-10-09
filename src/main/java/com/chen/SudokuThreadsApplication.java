package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class SudokuThreadsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SudokuThreadsApplication.class, args);
    }

}
