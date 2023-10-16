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
    void increaseTotalChapter(String bookId);
    void updateStarById(String id, double star);
    List<Book> getNewBookList();
    List<Book> getRecommendBookList();
    List<Book> getTop8NewBookList();
    List<Book> getTop6RecommendBookList();
    void updateTotalPurchasedById(String bookId, int totalPurchased);
    void updateTotalReviewById(String bookId, int totalReview);
    List<Book> getMostBuyBookList();
    List<Book> getMostReviewBookList();
    List<Book> getTop6MostBuyBookList();
    List<Book> getTop6MostReviewBookList();
    List<Book> searchBookFilter(int sort, int order, int status, int post, List<String> category, int page);
}
