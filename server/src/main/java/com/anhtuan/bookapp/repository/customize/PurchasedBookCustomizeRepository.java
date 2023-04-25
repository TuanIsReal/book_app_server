package com.anhtuan.bookapp.repository.customize;

public interface PurchasedBookCustomizeRepository {

    void updateLastReadChapterByBookIdAndUserId(String bookId, String userId, int chapterNumber);
}
