package com.addonis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableJpaRepositories(basePackages = {"com.addonis.demo.firstDB.repository", "com.addonis.demo.secondDB.secondRepository"})
@EnableTransactionManagement
//@EntityScan(basePackages = {"com.addonis.demo.firstDB.models"})
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
