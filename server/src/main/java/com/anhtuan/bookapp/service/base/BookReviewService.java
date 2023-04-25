package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.BookReview;

import java.util.List;

public interface BookReviewService {
    void addBookReview(BookReview bookReview);
    List<BookReview> getBookReviewByBookId(String bookId);
    List<BookReview> getBookReviewByBookIdAndAuthor(String bookId, String author);
    int countBookReviewsByBookId(String bookId);
}
