package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.BookChapter;
import com.anhtuan.bookapp.repository.customize.BookChapterCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookChapterRepository extends MongoRepository<BookChapter, String>, BookChapterCustomizeRepository {

    BookChapter findBookChapterByBookIdAndChapterNumber(String bookId, int chapterNumber);

    List<BookChapter> findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String bookId, int chapterNumber);

    List<BookChapter> findBookChaptersByBookId(String bookId);
}
