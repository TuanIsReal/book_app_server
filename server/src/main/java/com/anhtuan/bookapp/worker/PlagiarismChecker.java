package com.anhtuan.bookapp.worker;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.BookChapter;
import com.anhtuan.bookapp.domain.WarningChapter;
import com.anhtuan.bookapp.service.base.BookChapterService;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.STFService;
import com.anhtuan.bookapp.service.base.WarningChapterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.anhtuan.bookapp.config.Constant.TXT;

@Component
@AllArgsConstructor
@Slf4j
public class PlagiarismChecker extends Thread{
    private final ChapterContainer chapterContainer;
    private STFService stfService;
    private BookChapterService bookChapterService;
    private WarningChapterService warningChapterService;
    private BookService bookService;

    private static final Double MAX_SIMILAR_DOCUMENT = 60.0;

    @Override
    public void run() {
        while (true){
            try {
                String bookChapterId = chapterContainer.poll();
                if (bookChapterId != null && bookChapterService.getBookChapter(bookChapterId) != null) {
                    log.info("----Start check Plagiarism----");
                    BookChapter bookChapter = bookChapterService.getBookChapter(bookChapterId);
                    log.info("----Check Chapter: {} - {}", bookChapter.getId(), bookChapter.getChapterName());
                    List<BookChapter> verifyChapters = bookChapterService.findBookChaptersVerify(bookChapter.getBookId());
                    Map<BookChapter, String> verifyTexts = new HashMap<>();
                    verifyChapters.forEach(verifyChapter -> {
                        verifyTexts.put(verifyChapter, stfService.getChapterContent(verifyChapter.getChapterContent() + TXT));
                    });

                    log.info("----Verify Chapters Size: {}", verifyChapters.size());

                    Book book = bookService.findBookById(bookChapter.getBookId());
                    List<Book> books = bookService.findBooksUpByAuthor(book.getAuthor());
                    List<String> bookIds = books.stream().map(Book::getId).toList();

                    double similarDocument = 0;
                    String unVerifyText = stfService.getChapterContent(bookChapter.getChapterContent() + TXT);
                    BookChapter verifyChapter = null;

                    for (Map.Entry<BookChapter, String> entry : verifyTexts.entrySet()){
                        if (bookIds.contains(entry.getKey().getBookId())){
                            continue;
                        }
                        similarDocument = checkDocument(unVerifyText, entry.getValue());
                        if (similarDocument >= MAX_SIMILAR_DOCUMENT){
                            verifyChapter = entry.getKey();
                            break;
                        }

                    }
                    if (Objects.isNull(verifyChapter)){
                        bookChapterService.updateStatus(bookChapter.getId(), Constant.BOOK_CHAPTER_STATUS.VERIFY);
                        continue;
                    }

                    bookChapterService.updateStatus(bookChapter.getId(), Constant.BOOK_CHAPTER_STATUS.WARNING);
                    double similarityText = checkText(unVerifyText, verifyTexts.get(verifyChapter));
                    warningChapterService.insert(new WarningChapter(bookChapter.getId(), verifyChapter.getId(), similarDocument, similarityText));
                    WarningChapter warningChapter = new WarningChapter(bookChapter.getId(), verifyChapter.getId(), similarDocument, similarityText);

                    log.info("----WarningChapter: {}", warningChapter);

                    log.info("----End check Plagiarism----");
                } else {
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }

    private double checkDocument(String unVerifyText, String verifyText){
        LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
        int distance = levenshteinDistance.apply(unVerifyText, verifyText);

        int maxLength = Math.max(unVerifyText.length(), verifyText.length());
        double similarity = 1.0 - (double) distance / maxLength;
        return similarity * 100;
    }

    private double checkText(String unVerifyText, String verifyText){
        Map<CharSequence, Integer> document1 = createWordFrequencyMap(verifyText);
        Map<CharSequence, Integer> document2 = createWordFrequencyMap(unVerifyText);


        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        return cosineSimilarity.cosineSimilarity(document1, document2) * 100;
    }

    private static Map<CharSequence, Integer> createWordFrequencyMap(String text) {
        Map<CharSequence, Integer> wordFrequencyMap = new HashMap<>();
        String[] words = text.split("\\s+");

        for (String word : words) {
            word = word.toLowerCase();
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }

        return wordFrequencyMap;
    }
}
