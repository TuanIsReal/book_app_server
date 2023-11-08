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
public class PlagiarismChecker {
    private STFService stfService;
    private BookChapterService bookChapterService;
    private WarningChapterService warningChapterService;
    private BookService bookService;

    private static final Double MAX_SIMILAR_DOCUMENT = 60.0;

    @Scheduled(fixedDelay = 600000, initialDelay = 5000)
    private void checkPlagiarism() {
        log.info("------Start check Plagiarism-----");
        List<BookChapter> unVerifyChapters = bookChapterService.findBookChaptersNotVerify();

        System.out.println("----UnVerify Chapters Size: " + unVerifyChapters.size());
        if (unVerifyChapters.isEmpty()){
            log.info("------No Chapter to check");
            return;
        }

        List<String> unVerifyBookIds = unVerifyChapters.stream().map(BookChapter::getBookId).toList();

        List<BookChapter> verifyChapters = bookChapterService.findBookChaptersVerify(unVerifyBookIds);
        Map<BookChapter, String> verifyTexts = new HashMap<>();
        verifyChapters.forEach(verifyChapter -> {
            verifyTexts.put(verifyChapter, stfService.getChapterContent(verifyChapter.getChapterContent() + TXT));
        });

        log.info("------Verify Chapters Size: " + verifyChapters.size());

        for (BookChapter unVerifyChapter : unVerifyChapters){
            Book book = bookService.findBookById(unVerifyChapter.getBookId());
            List<Book> books = bookService.findBooksUpByAuthor(book.getAuthor());
            List<String> bookIds = books.stream().map(Book::getId).toList();

            log.info("------Check Chapter: " + unVerifyChapter.getChapterName());

            double similarDocument = 0;
            String unVerifyText = stfService.getChapterContent(unVerifyChapter.getChapterContent() + TXT);
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
                bookChapterService.updateStatus(unVerifyChapter.getId(), Constant.BOOK_CHAPTER_STATUS.VERIFY);
                continue;
            }

            bookChapterService.updateStatus(unVerifyChapter.getId(), Constant.BOOK_CHAPTER_STATUS.WARNING);
            double similarityText = checkText(unVerifyText, verifyTexts.get(verifyChapter));
            warningChapterService.insert(new WarningChapter(unVerifyChapter.getId(), verifyChapter.getId(), similarDocument, similarityText));
            WarningChapter warningChapter = new WarningChapter(unVerifyChapter.getId(), verifyChapter.getId(), similarDocument, similarityText);

            log.info("------WarningChapter: "+ warningChapter);
        }

        log.info("------End check Plagiarism-----");
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
