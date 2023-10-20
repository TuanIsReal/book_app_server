package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.repository.customize.BookCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomizeRepository {

    Book findBookByBookNameAndStatusGreaterThanEqual(String bookName, Integer status);

    Book findBookById(String id);

    List<Book> findBooksByAuthorAndStatus(String userId, Integer status);

    List<Book> findBooksByAuthorAndStatusGreaterThanEqual(String userId, Integer status);

    List<Book> findBooksByAuthorAndStatusGreaterThanEqualAndIdIsNot(String author, Integer status, String bookId);

    List<Book> findBooksByIdInAndStatusGreaterThanEqual(List<String> idList, Integer status);

}
