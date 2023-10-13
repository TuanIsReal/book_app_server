package com.anhtuan.bookapp;

import com.anhtuan.bookapp.service.base.STFService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
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
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
@AllArgsConstructor
@EnableConfigurationProperties
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
