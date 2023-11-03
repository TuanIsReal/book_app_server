package com.anhtuan.bookapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AllArgsConstructor
@EnableConfigurationProperties
@EnableScheduling
@SecurityScheme(name = "scheme security",type = SecuritySchemeType.HTTP,bearerFormat = "JWT",scheme ="Bearer",in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Book App API",
		version = "3.0",
		description = "Book App API"),
		security ={@SecurityRequirement(name = "scheme security")})
public class BookAppServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookAppServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
