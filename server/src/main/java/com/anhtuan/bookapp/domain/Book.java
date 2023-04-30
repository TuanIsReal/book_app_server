package com.anhtuan.bookapp.domain;

import com.anhtuan.bookapp.request.AddBookRequest;
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
    public static final String USER_POST = "user_post";
    public static final String INTRODUCTION = "introduction";
    public static final String BOOK_IMAGE = "book_image";
    public static final String BOOK_CATEGORY = "book_category";
    public static final String BOOK_PRICE = "book_price";
    public static final String STAR = "star";
    public static final String TOTAL_CHAPTER = "total_chapter";
    public static final String TOTAL_PURCHASED = "total_purchased";
    public static final String TOTAL_REVIEW = "total_review";
    public static final String UPLOAD_TIME = "upload_time";
    public static final String LAST_UPDATE_TIME = "last_update_time";
    public static final String COMPLETE_BOOK = "complete_book";
    public static final String ADMIN_UP = "admin_up";

    @Id
    private String id;

    @Field(BOOK_NAME)
    private String bookName;

    @Field(AUTHOR)
    private String author;

    @Field(USER_POST)
    private String userPost;

    @Field(INTRODUCTION)
    private String introduction;

    @Field(BOOK_IMAGE)
    private String bookImage;

    @Field(BOOK_CATEGORY)
    private List<String> bookCategory;

    @Field(BOOK_PRICE)
    private int bookPrice;

    @Field(STAR)
    private double star;

    @Field(TOTAL_CHAPTER)
    private int totalChapter;

    @Field(TOTAL_PURCHASED)
    private int totalPurchased;

    @Field(TOTAL_REVIEW)
    private int totalReview;

    @Field(UPLOAD_TIME)
    private long uploadTime;

    @Field(LAST_UPDATE_TIME)
    private long lastUpdateTime;

    @Field(COMPLETE_BOOK)
    private boolean completeBook;

    @Field(ADMIN_UP)
    private boolean adminUp;

    public Book() {
    }

    public Book(AddBookRequest request) {
        this.userPost = request.getUserPost()
;        this.bookName = request.getBookName();
        this.author = request.getAuthor();
        this.introduction = request.getIntroduction();
        this.bookImage = request.getBookImage();
        this.bookPrice = request.getBookPrice();
    }

    public Book(BookRequestUp book) {
        this.id = book.getId();
        this.userPost = book.getUserPost();
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.introduction = book.getIntroduction();
        this.bookImage = book.getBookImage();
        this.bookPrice = book.getBookPrice();
        this.bookCategory = book.getBookCategory();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
