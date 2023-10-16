package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.BookChapter;

import java.util.List;

public interface BookChapterService {

    String insertBookChapter(BookChapter bookChapter);

    BookChapter findBookChapterByBookIdAndChapterNumber(String bookId, int chapterNumber);

    List<BookChapter> getBookChaptersByBookId(String bookId);

    List<BookChapter> findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String bookId, int chapterNumber);
}
