package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.config.Constant;
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
    public String insertBookChapter(BookChapter bookChapter) {
        String chapterId = bookChapterRepository.insert(bookChapter).getId();
        bookChapterRepository.updateChapterContent(chapterId, chapterId);
        return chapterId;
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

    @Override
    public List<BookChapter> findBookChaptersVerify(List<String> bookIds) {
        return bookChapterRepository.findBookChaptersByBookIdNotInAndStatus(bookIds, Constant.BOOK_CHAPTER_STATUS.VERIFY);
    }

    @Override
    public List<BookChapter> findBookChaptersNotVerify() {
        return bookChapterRepository.findBookChaptersByStatus(Constant.BOOK_CHAPTER_STATUS.NOT_VERIFY);
    }

    @Override
    public void updateStatus(String chapterId, int status) {
        bookChapterRepository.updateStatus(chapterId, status);
    }

    @Override
    public void deleteById(String id) {
        bookChapterRepository.deleteById(id);
    }

    @Override
    public BookChapter getBookChapter(String id) {
        return bookChapterRepository.findBookChapterById(id);
    }


}
