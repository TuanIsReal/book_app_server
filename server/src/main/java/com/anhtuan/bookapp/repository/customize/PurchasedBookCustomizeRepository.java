package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.PurchasedBook;

import java.util.List;

public interface PurchasedBookCustomizeRepository {

    void updateLastReadChapterByBookIdAndUserId(String bookId, String userId, int chapterNumber);
    void updateShowBookByBookIdAndUserId(String bookId, String userId, boolean status);
    List<PurchasedBook> getPurchasedSpendPoint();
}
