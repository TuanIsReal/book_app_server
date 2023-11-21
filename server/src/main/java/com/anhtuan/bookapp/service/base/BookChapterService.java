package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.BookChapter;

import java.util.List;

public interface BookChapterService {

    String insertBookChapter(BookChapter bookChapter);

    BookChapter findBookChapterByBookIdAndChapterNumber(String bookId, int chapterNumber);

    List<BookChapter> getBookChaptersByBookId(String bookId);

    List<BookChapter> findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String bookId, int chapterNumber);

    List<BookChapter> findBookChaptersVerify(String bookId);
    List<BookChapter> findBookChaptersNotVerify();

    void updateStatus(String chapterId, int status);

    void deleteById(String id);

    BookChapter getBookChapter(String id);

    void actionUploadChapter(BookChapter chapter, Book book);
    List<BookChapter> findChapterByIds(List<String> ids);
}
