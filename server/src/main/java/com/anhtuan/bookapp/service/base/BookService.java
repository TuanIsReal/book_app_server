package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Book;

import java.util.List;

public interface BookService {

    void insertBook(Book book);

    Book findBookByBookName(String bookName);

    Book findBookById(String bookId);

    List<Book> findBooksByUserPost(String userId);

    void updateBookImageByBookId(String bookId, String bookImage);

    List<Book> findBookByText(String text);

    List<Book> findBooksByAuthorAndBookNameIsNot(String author, String bookName);

    List<Book> getBooksByAuthorAndIdIsNot(String author, String bookId);

    List<Book> getBooksByIdList(List<String> idList);

    void updateTotalChapterById(String bookId, int totalChapter);
    void updateStarById(String id, double star);
    List<Book> getNewBookList();
    List<Book> getRecommendBookList();
}
