package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.BookReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookReviewRepository extends MongoRepository<BookReview, String> {
    List<BookReview> findBookReviewsByBookId(String bookId);
    BookReview findBookReviewByBookIdAndAuthor(String bookId, String author);
    int countBookReviewsByBookId(String bookId);
}
