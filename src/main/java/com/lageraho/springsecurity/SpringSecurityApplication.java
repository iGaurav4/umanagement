package com.lageraho.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@EntityScan(basePackages = "com.lageraho.springsecurity.model")
//@EnableJpaRepositories(basePackages = "com.lageraho.springsecurity.repository")
@EnableMongoRepositories(basePackages = "com.lageraho.springsecurity.repository")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

}
