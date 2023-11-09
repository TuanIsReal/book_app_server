package com.anhtuan.bookapp.domain;

import com.anhtuan.bookapp.request.AddBookRequest;
import com.anhtuan.bookapp.request.UpdateBookRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = Book.BOOK_COLLECTION)
public class Book {

    public static final String BOOK_COLLECTION = "book";

    public static final String ID = "_id";
    public static final String BOOK_NAME = "book_name";
    public static final String AUTHOR = "author";
    public static final String INTRODUCTION = "introduction";
    public static final String BOOK_IMAGE = "book_image";
    public static final String BOOK_CATEGORY = "book_category";
    public static final String BOOK_PRICE = "book_price";
    public static final String FREE_CHAPTER = "free_chapter";
    public static final String STAR = "star";
    public static final String TOTAL_CHAPTER = "total_chapter";
    public static final String TOTAL_PURCHASED = "total_purchased";
    public static final String TOTAL_REVIEW = "total_review";
    public static final String REQUEST_TIME = "request_time";
    public static final String UPLOAD_TIME = "upload_time";
    public static final String LAST_UPDATE_TIME = "last_update_time";
    public static final String STATUS = "status";
    public static final String ADMIN_UP = "admin_up";

    @Id
    private String id;

    @Field(BOOK_NAME)
    private String bookName;

    @Field(AUTHOR)
    private String author;

    @Field(INTRODUCTION)
    private String introduction;

    @Field(BOOK_IMAGE)
    private String bookImage;

    @Field(BOOK_CATEGORY)
    private List<String> bookCategory;

    @Field(BOOK_PRICE)
    private int bookPrice;

    @Field(FREE_CHAPTER)
    private int freeChapter;

    @Field(STAR)
    private double star;

    @Field(TOTAL_CHAPTER)
    private int totalChapter;

    @Field(TOTAL_PURCHASED)
    private int totalPurchased;

    @Field(TOTAL_REVIEW)
    private int totalReview;

    @Field(REQUEST_TIME)
    private long requestTime;

    @Field(UPLOAD_TIME)
    private long uploadTime;

    @Field(LAST_UPDATE_TIME)
    private long lastUpdateTime;

    @Field(STATUS)
    private int status;

    @Field(ADMIN_UP)
    private boolean adminUp;

    public Book() {
    }

    public Book(AddBookRequest request) {
        this.bookName = request.getBookName();
        this.introduction = request.getIntroduction();
        this.bookImage = request.getBookImage();
        this.bookPrice = request.getBookPrice();
        this.freeChapter = request.getFreeChapter();
    }

    public Book(UpdateBookRequest request) {
        this.id = request.getBookId();
        this.introduction = request.getIntroduction();
        this.bookPrice = request.getBookPrice();
        this.freeChapter = request.getFreeChapter();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
