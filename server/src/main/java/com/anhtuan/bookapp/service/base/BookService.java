package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Book;

import java.util.List;

public interface BookService {

    void insertBook(Book book);

    Book findBookByBookName(String bookName);

    Book findBookById(String bookId);

    List<Book> findBooksByAuthorAndStatus(String userId, Integer status);

    List<Book> findBooksUpByAuthor(String userId);

    void updateBookImageByBookId(String bookId, String bookImage);

    List<Book> findBookByText(String text);

    List<Book> getBooksByAuthorAndIdIsNot(String author, String bookId);

    List<Book> getBooksByIdList(List<String> idList);
    void increaseTotalChapter(String bookId, int number);
    void updateStarById(String id, double star);
    void increaseTotalReview(String bookId);
    void increaseTotalPurchased(String bookId);
    List<Book> searchBookFilter(int sort, int order, int status, int post, List<String> category, int page);
    List<Book> getBooksHome(int type, int limit);
    List<Book> getBookByStatus(int status);
    void updateBookStatus(String bookId, int status);
    void updateBookInfo(Book book, boolean isFinish);
    List<Book> findBookByBookIdList(List<String> ids);
    int countBooksByAuthor(String author);
}
