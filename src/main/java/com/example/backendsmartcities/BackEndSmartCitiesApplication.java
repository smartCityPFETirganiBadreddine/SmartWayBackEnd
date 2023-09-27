package com.example.backendsmartcities;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Author: Badreddine TIRGANI
 */
@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "typi API", description = "challenge app Road application"))
public class BackEndSmartCitiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndSmartCitiesApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



}
