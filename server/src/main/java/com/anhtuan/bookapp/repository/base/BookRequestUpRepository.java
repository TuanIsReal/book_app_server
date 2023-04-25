package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.BookRequestUp;
import com.anhtuan.bookapp.repository.customize.BookRequestUpCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRequestUpRepository extends MongoRepository<BookRequestUp, String>, BookRequestUpCustomizeRepository {

    BookRequestUp findBookRequestUpByBookNameAndStatus(String bookName, int status);
    BookRequestUp findBookRequestUpByIdAndStatus(String id, int status);
    void deleteBookRequestUpById(String id);
    List<BookRequestUp> findBookRequestUpsByUserPostAndStatus(String userPost, int status);
    List<BookRequestUp> findBookRequestUpsByStatus(int status);
}
