package com.anhtuan.bookapp;

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
public class BookAppServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookAppServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
