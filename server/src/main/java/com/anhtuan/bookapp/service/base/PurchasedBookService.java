package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.PurchasedBook;

import java.util.List;

public interface PurchasedBookService {
    void insertPuchasedBook(PurchasedBook purchasedBook);

    PurchasedBook getPurchasedBookByBookIdAndUserId(String bookId, String userId);

    void updateLastReadChapterByBookIdAndUserId(String bookId, String userId, int chapterNumber);

    List<PurchasedBook> getPurchasedBooksByUserIdAndShowLibrary(String userId, boolean showLibrary);

    int countPurchasedBooksByBookIdAndUserIdIsNot(String bookId, String userId);

    List<PurchasedBook> findPurchasedBooksByBookIdAndUserIdIsNot(String bookId, String userId);

    void unShowPurchasedBook(String bookId, String userId);
    List<PurchasedBook> getPurchasedSpendPoint();
}
