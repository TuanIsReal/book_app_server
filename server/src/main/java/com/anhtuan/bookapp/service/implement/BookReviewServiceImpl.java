package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.BookReview;
import com.anhtuan.bookapp.repository.base.BookReviewRepository;
import com.anhtuan.bookapp.service.base.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Override
    public void addBookReview(BookReview bookReview) {
        bookReviewRepository.insert(bookReview);
    }

    @Override
    public List<BookReview> getBookReviewByBookId(String bookId) {
        return bookReviewRepository.findBookReviewsByBookId(bookId);
    }

    @Override
    public List<BookReview> getBookReviewByBookIdAndAuthor(String bookId, String author) {
        return bookReviewRepository.findBookReviewsByBookIdAndAuthor(bookId, author);
    }

    @Override
    public int countBookReviewsByBookId(String bookId) {
        return bookReviewRepository.countBookReviewsByBookId(bookId);
    }
}
