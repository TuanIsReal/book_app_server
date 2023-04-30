package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.repository.customize.BookCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomizeRepository {

    Book findBookByBookName(String bookName);

    Book findBookById(String id);

    List<Book> findBooksByUserPost(String userId);

    List<Book> findBooksByAuthorAndBookNameIsNot(String author, String bookName);

    List<Book> findBooksByAuthorAndIdIsNot(String author, String bookId);

    List<Book> findBooksByIdIn(List<String> idList);

    List<Book> findTop8ByOrderByUploadTimeDesc();

    List<Book> findTop6ByOrderByStarDesc();
    List<Book> findBooksByIdIsIn(List<String> idList);
    List<Book> findBooksByOrderByUploadTimeDesc();
    List<Book> findBooksByOrderByStarDesc();
    List<Book> findBooksByOrderByTotalPurchasedDesc();
    List<Book> findTop6ByOrderByTotalPurchasedDesc();
    List<Book> findBooksByOrderByTotalReviewDesc();
    List<Book> findTop6ByOrderByTotalReviewDesc();

}
