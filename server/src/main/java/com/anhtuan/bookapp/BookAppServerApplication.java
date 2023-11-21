package com.anhtuan.bookapp;

import com.anhtuan.bookapp.cache.UserInfoManager;
import com.anhtuan.bookapp.domain.BookChapter;
import com.anhtuan.bookapp.service.base.BookChapterService;
import com.anhtuan.bookapp.worker.ChapterContainer;
import com.anhtuan.bookapp.worker.WorkerManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@EnableConfigurationProperties
@EnableScheduling
@Slf4j
public class BookAppServerApplication implements CommandLineRunner {

	private final UserInfoManager userInfoManager;
	private final WorkerManager workerManager;
	private final BookChapterService bookChapterService;
	private final ChapterContainer chapterContainer;

	public static void main(String[] args) {
		SpringApplication.run(BookAppServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("---SERVER STARTING---");
		log.info("INIT USER CACHE");
		userInfoManager.initData();
		log.info("DONE INIT USER CACHE, SIZE: {}", userInfoManager.getSize());
		List<BookChapter> unVerifyChapters = bookChapterService.findBookChaptersNotVerify();
		workerManager.startWorkers();
		if (!unVerifyChapters.isEmpty()){
			unVerifyChapters.forEach(chapter -> chapterContainer.add(chapter.getId()));
		}
		log.info("---SERVER STARTED---");
	}
}
