package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.BookRequestUp;

import java.util.List;

public interface BookRequestUpService {
    BookRequestUp getBookRequestUpByBookNameAndStatus(String bookName, int status);
    void addBookRequestUp(BookRequestUp bookRequestUp);
    void updateBookImageById(String id, String bookImage);
    BookRequestUp getBookRequestUp(String id, int status);
    void updateStatusById(String id, int status);
    void deleteBookRequestUpById(String id);
    List<BookRequestUp> getBookRequestUpsByUserPostAndStatus(String userPost, int status);
    List<BookRequestUp> getBookRequestUpsByStatus(int status);
}
