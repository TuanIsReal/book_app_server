package com.anhtuan.bookapp.domain;

import com.anhtuan.bookapp.request.AddBookRequest;
import com.anhtuan.bookapp.request.AddBookRequestUpRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = BookRequestUp.BOOK_REQUEST_UP_COLLECTION)
public class BookRequestUp {
    public static final String BOOK_REQUEST_UP_COLLECTION = "book_request_up";
    public static final String ID = "_id";
    public static final String BOOK_NAME = "book_name";
    public static final String AUTHOR = "author";
    public static final String USER_POST = "user_post";
    public static final String INTRODUCTION = "introduction";
    public static final String BOOK_IMAGE = "book_image";
    public static final String BOOK_CATEGORY = "book_category";
    public static final String BOOK_PRICE = "book_price";
    public static final String STATUS = "status";
    public static final String REQUEST_TIME = "request_time";

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

    @Field(STATUS)
    private int status;

    @Field(REQUEST_TIME)
    private long requestTime;

    public BookRequestUp() {
    }


    public BookRequestUp(AddBookRequest request) {
        this.bookName = request.getBookName();
        this.author = request.getAuthor();
        this.introduction = request.getIntroduction();
        this.bookImage = request.getBookImage();
        this.bookPrice = request.getBookPrice();
    }
}
