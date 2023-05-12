package com.anhtuan.bookapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = PurchasedBook.PURCHASED_BOOK_COLLECTION)
public class PurchasedBook {

    public static final String PURCHASED_BOOK_COLLECTION = "purchased_book";
    public static final String BOOK_ID = "book_id";
    public static final String USER_ID = "user_id";
    public static final String BOOK_NAME = "book_name";
    public static final String LAST_READ_CHAPTER = "last_read_chapter";
    public static final String LAST_READ_TIME = "last_read_time";
    public static final String PAYMENT_POINT = "payment_point";
    public static final String PURCHASED_TIME = "purchased_time";
    public static final String SHOW_LIBRARY = "show_library";

    @Id
    private String id;

    @Field(BOOK_ID)
    private String bookId;

    @Field(USER_ID)
    private String userId;

    @Field(BOOK_NAME)
    private String bookName;

    @Field(LAST_READ_CHAPTER)
    private int lastReadChapter;

    @Field(LAST_READ_TIME)
    private long lastReadTime;

    @Field(PAYMENT_POINT)
    private int paymentPoint;

    @Field(PURCHASED_TIME)
    private long purchasedTime;

    @Field(SHOW_LIBRARY)
    private boolean showLibrary;


    public PurchasedBook(String bookId, String userId, String bookName, int lastReadChapter, long lastReadTime, int paymentPoint, long purchasedTime, boolean showLibrary) {
        this.bookId = bookId;
        this.userId = userId;
        this.bookName = bookName;
        this.lastReadChapter = lastReadChapter;
        this.lastReadTime = lastReadTime;
        this.paymentPoint = paymentPoint;
        this.purchasedTime = purchasedTime;
        this.showLibrary = showLibrary;
    }


}
