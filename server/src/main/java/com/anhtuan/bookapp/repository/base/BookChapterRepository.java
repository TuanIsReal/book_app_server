package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.BookChapter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookChapterRepository extends MongoRepository<BookChapter, String> {

    BookChapter findBookChapterByBookIdAndChapterNumber(String bookId, int chapterNumber);

    List<BookChapter> findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String bookId, int chapterNumber);

    List<BookChapter> findBookChaptersByBookId(String bookId);
}
