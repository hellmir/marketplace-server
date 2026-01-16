package com.personal.marketnote.reward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.personal.marketnote"
})
public class RewardApplication {
    public static void main(String[] args) {
        SpringApplication.run(RewardApplication.class, args);
    }
}
