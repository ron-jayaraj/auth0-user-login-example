package com.ron.sell.online.sellonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = { "com.ron.*" })
@EnableJpaRepositories("com.ron.sell.online.repository")

public class SellonlineApplication {

    // ********************* see config folder for database connection

    // *****************
    public static void main(String[] args) {
        SpringApplication.run(SellonlineApplication.class, args);
        
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}




