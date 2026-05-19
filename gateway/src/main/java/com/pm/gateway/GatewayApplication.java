package com.pm.gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public CommandLineRunner printEnv(Environment env) {
        return args -> {
            System.out.println("Port: " + env.getProperty("server.port"));
            System.out.println("Route URI: " + env.getProperty("spring.cloud.gateway.server.webflux.routes[0].uri"));
        };
    }

}
