package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.repository.customize.BookCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomizeRepository {

    Book findBookByBookName(String bookName);

    Book findBookById(String id);

    List<Book> findBooksByAuthorAndStatus(String userId, Integer status);

    List<Book> findBooksByAuthorAndStatusAndIdIsNot(String author, Integer status, String bookId);

    List<Book> findBooksByIdInAndStatus(List<String> idList, Integer status);

    List<Book> findBooksByStatusTop8ByOrderByUploadTimeDesc();

    List<Book> findTop6ByOrderByStarDesc();
    List<Book> findBooksByIdIsIn(List<String> idList);
    List<Book> findBooksByOrderByUploadTimeDesc();
    List<Book> findBooksByOrderByStarDesc();
    List<Book> findBooksByOrderByTotalPurchasedDesc();
    List<Book> findTop6ByOrderByTotalPurchasedDesc();
    List<Book> findBooksByOrderByTotalReviewDesc();
    List<Book> findTop6ByOrderByTotalReviewDesc();

}
