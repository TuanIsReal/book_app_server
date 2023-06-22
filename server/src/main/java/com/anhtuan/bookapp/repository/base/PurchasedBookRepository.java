package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.PurchasedBook;
import com.anhtuan.bookapp.repository.customize.PurchasedBookCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchasedBookRepository extends MongoRepository<PurchasedBook, String>, PurchasedBookCustomizeRepository {

    PurchasedBook findPurchasedBookByBookIdAndUserId(String bookId, String userId);

    List<PurchasedBook> findPurchasedBooksByUserIdAndShowLibraryOrderByLastReadTimeDesc(String userId, boolean showLibrary);

    int countPurchasedBooksByBookIdAndUserIdIsNot(String bookId, String userId);

    List<PurchasedBook> findPurchasedBooksByBookIdAndUserIdIsNot(String bookId, String userId);
}
