package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.repository.customize.BookCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomizeRepository {

    Book findBookByBookNameAndStatusGreaterThanEqual(String bookName, Integer status);
    List<Book> findBooksByStatus(Integer status);

    Book findBookById(String id);

    List<Book> findBooksByAuthorAndStatus(String author, Integer status);

    List<Book> findBooksByAuthorAndStatusGreaterThanEqual(String author, Integer status);

    List<Book> findBooksByAuthorAndStatusGreaterThanEqualAndIdIsNot(String author, Integer status, String bookId);

    List<Book> findBooksByIdInAndStatusGreaterThanEqual(List<String> idList, Integer status);

    int countBooksByAuthorAndStatusGreaterThanEqual(String author, Integer status);

}
