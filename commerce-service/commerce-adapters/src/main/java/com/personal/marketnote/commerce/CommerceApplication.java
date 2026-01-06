package com.personal.marketnote.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.personal.marketnote"
})
public class CommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommerceApplication.class, args);
    }
}
