package com.accountBook.accountBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AccountBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountBookApplication.class, args);
    }
}