package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.Book;

import java.util.List;

public interface BookCustomizeRepository {
    void updateBookImageByBookId(String bookId, String bookImage);
    List<Book> findBookByText(String text);
    void updateStarById(String id, double star);
    List<Book> searchBookFilter(String sort,int order, int status, int post, List<String> category, int page);
    void increaseTotalChapter(String bookId, int number);
    void increaseTotalReview(String bookId);
    void increaseTotalPurchased(String bookId);
    List<Book> findBookHome(int type, int limit);
    void updateBookStatus(String bookId, int status);

    void updateBookInfo(Book book, boolean isFinish);
    List<Book> findBookByBookIdList(List<String> ids);
}
