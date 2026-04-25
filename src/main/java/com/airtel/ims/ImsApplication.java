package com.airtel.ims;

import com.airtel.ims.authentication.LoginService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ImsApplication.class, args);
        System.out.println("========================================");
        System.out.println("Airtel Inventory Management System Started!");
        System.out.println("Open your browser and go to: http://localhost:8080");
        System.out.println("This project done by Emmyson & Kevine");
        System.out.println("========================================");
    }
    
    @Bean
    public CommandLineRunner init(LoginService loginService) {
        return args -> {
            // Create default admin user on startup
            loginService.createDefaultAdmin();
        };
    }
}