package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.PurchasedBook;
import com.anhtuan.bookapp.repository.base.PurchasedBookRepository;
import com.anhtuan.bookapp.service.base.PurchasedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasedBookServiceImpl implements PurchasedBookService {

    @Autowired
    private PurchasedBookRepository purchasedBookRepository;

    @Override
    public void insertPuchasedBook(PurchasedBook purchasedBook) {
        purchasedBookRepository.insert(purchasedBook);
    }

    @Override
    public PurchasedBook getPurchasedBookByBookIdAndUserId(String bookId, String userId) {
        return purchasedBookRepository.findPurchasedBookByBookIdAndUserId(bookId, userId);
    }

    @Override
    public void updateLastReadChapterByBookIdAndUserId(String bookId, String userId, int chapterNumber) {
        purchasedBookRepository.updateLastReadChapterByBookIdAndUserId(bookId, userId, chapterNumber);
    }

    @Override
    public List<PurchasedBook> getPurchasedBooksByUserIdAndShowLibrary(String userId, boolean showLibrary) {
        return purchasedBookRepository.findPurchasedBooksByUserIdAndShowLibrary(userId, showLibrary);
    }

    @Override
    public int countPurchasedBooksByBookIdAndUserIdIsNot(String bookId, String userId) {
        return purchasedBookRepository.countPurchasedBooksByBookIdAndUserIdIsNot(bookId, userId);
    }

    @Override
    public List<PurchasedBook> findPurchasedBooksByBookIdAndUserIdIsNot(String bookId, String userId) {
        return purchasedBookRepository.findPurchasedBooksByBookIdAndUserIdIsNot(bookId, userId);
    }
}
