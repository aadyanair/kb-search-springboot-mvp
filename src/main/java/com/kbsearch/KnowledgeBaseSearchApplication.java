package com.kbsearch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.kbsearch")
public class KnowledgeBaseSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeBaseSearchApplication.class, args);
    }
}
