package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.BookChapter;
import com.anhtuan.bookapp.repository.base.BookChapterRepository;
import com.anhtuan.bookapp.service.base.BookChapterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookChapterServiceImpl implements BookChapterService {

    private BookChapterRepository bookChapterRepository;

    @Override
    public void insertBookChapter(BookChapter bookChapter) {
        bookChapterRepository.insert(bookChapter);
    }

    @Override
    public BookChapter findBookChapterByBookIdAndChapterNumber(String bookId, int chapterNumber) {
        return bookChapterRepository.findBookChapterByBookIdAndChapterNumber(bookId, chapterNumber);
    }

    @Override
    public List<BookChapter> getBookChaptersByBookId(String bookId) {
        return bookChapterRepository.findBookChaptersByBookId(bookId);
    }

    @Override
    public List<BookChapter> findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String bookId, int chapterNumber) {
        return bookChapterRepository.findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(bookId, chapterNumber);
    }


}
