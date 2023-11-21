package com.anhtuan.bookapp.worker;

import com.anhtuan.bookapp.domain.BookChapter;
import com.anhtuan.bookapp.service.base.BookChapterService;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.STFService;
import com.anhtuan.bookapp.service.base.WarningChapterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@AllArgsConstructor
public class ChapterContainer {
    private STFService stfService;
    private BookChapterService bookChapterService;
    private WarningChapterService warningChapterService;
    private BookService bookService;
    private final ConcurrentLinkedQueue<String> CHAPTER_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Integer MAX_THREAD = 2;


    public synchronized void add(String bookChapter) {
        CHAPTER_QUEUE.add(bookChapter);
    }

    public synchronized String poll() {
        return CHAPTER_QUEUE.poll();
    }

    public void startRunners() {
        for (int i = 0; i < MAX_THREAD; i++) {
            PlagiarismChecker plagiarismChecker = new PlagiarismChecker(this, stfService, bookChapterService, warningChapterService, bookService);
            plagiarismChecker.start();
        }
    }

    public int size() {
        return CHAPTER_QUEUE.size();
    }
}
