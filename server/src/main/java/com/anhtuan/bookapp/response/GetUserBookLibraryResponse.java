package com.anhtuan.bookapp.response;

import com.anhtuan.bookapp.domain.PurchasedBook;
import lombok.Data;

@Data
public class GetUserBookLibraryResponse {
    private String bookId;
    private String bookName;
    private int lastRead;
    private int totalChapter;
    private String bookImage;

    public GetUserBookLibraryResponse(PurchasedBook purchasedBook) {
        this.bookId = purchasedBook.getBookId();
        this.bookName = purchasedBook.getBookName();
        this.lastRead = purchasedBook.getLastReadChapter();
    }
}
