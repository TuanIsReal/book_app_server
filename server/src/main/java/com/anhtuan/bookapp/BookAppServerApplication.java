package com.anhtuan.bookapp;

import com.anhtuan.bookapp.cache.UserInfoManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AllArgsConstructor
@EnableConfigurationProperties
@EnableScheduling
@Slf4j
public class BookAppServerApplication implements CommandLineRunner {

	private UserInfoManager userInfoManager;

	public static void main(String[] args) {
		SpringApplication.run(BookAppServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("---SERVER STARTING---");
		log.info("INIT USER CACHE");
		userInfoManager.initData();
		log.info("DONE INIT USER CACHE, SIZE: {}", userInfoManager.getSize());
		log.info("---SERVER STARTED---");
	}
}
