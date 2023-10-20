package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.Book;

import java.util.List;

public interface BookCustomizeRepository {
    void updateBookImageByBookId(String bookId, String bookImage);

    List<Book> findBookByText(String text);
    void updateStarById(String id, double star);
    void updateTotalPurchasedById(String id, int totalPurchased);
    void updateTotalReviewById(String id, int totalReview);
    List<Book> searchBookFilter(String sort,int order, int status, int post, List<String> category, int page);
    void increaseTotalChapter(String bookId);
    List<Book> findBookHome(int type, int limit);
}
