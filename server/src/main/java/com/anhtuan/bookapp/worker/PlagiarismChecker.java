package com.anhtuan.bookapp.worker;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.BookChapter;
import com.anhtuan.bookapp.domain.WarningChapter;
import com.anhtuan.bookapp.service.base.BookChapterService;
import com.anhtuan.bookapp.service.base.STFService;
import com.anhtuan.bookapp.service.base.WarningChapterService;
import lombok.AllArgsConstructor;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.anhtuan.bookapp.config.Constant.TXT;

@Component
@AllArgsConstructor
public class PlagiarismChecker {
    private STFService stfService;
    private BookChapterService bookChapterService;
    private WarningChapterService warningChapterService;

    private static final Double MAX_SIMILAR_DOCUMENT = 50.0;

    @Scheduled(fixedDelay = 50000, initialDelay = 10000)
    private void checkPlagiarism() {
        System.out.println("------Start check Plagiarism-----");
        List<BookChapter> unVerifyChapters = bookChapterService.findBookChaptersNotVerify();
        if (unVerifyChapters.isEmpty()){
            System.out.println("------No Chapter to check");
            return;
        }

        List<String> unVerifyBookIds = unVerifyChapters.stream().map(BookChapter::getBookId).toList();
        List<BookChapter> verifyChapters = bookChapterService.findBookChaptersVerify(unVerifyBookIds);
        Map<String, String> verifyTexts = new HashMap<>();
        verifyChapters.forEach(verifyChapter -> {
            verifyTexts.put(verifyChapter.getId(), stfService.getChapterContent(verifyChapter.getChapterContent() + TXT));
        });

        for (BookChapter unVerifyChapter : unVerifyChapters){
            String unVerifyText = stfService.getChapterContent(unVerifyChapter.getChapterContent() + TXT);
            String verifyChapterId = null;
            double similarDocument = 0;
            for (Map.Entry<String, String> entry : verifyTexts.entrySet()){
                similarDocument = checkDocument(unVerifyText, entry.getValue());
                if (similarDocument >= MAX_SIMILAR_DOCUMENT){
                    verifyChapterId = entry.getKey();
                    return;
                }
            }
            if (Objects.isNull(verifyChapterId)){
                bookChapterService.updateStatus(unVerifyChapter.getId(), Constant.BOOK_CHAPTER_STATUS.VERIFY);
                continue;
            }

            bookChapterService.updateStatus(unVerifyChapter.getId(), Constant.BOOK_CHAPTER_STATUS.WARNING);
            double similarityText = checkText(unVerifyText, verifyTexts.get(verifyChapterId));
            warningChapterService.insert(new WarningChapter(unVerifyChapter.getId(), verifyChapterId, similarDocument, similarityText));
        }

        System.out.println("------END check Plagiarism-----");
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
