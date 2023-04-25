package com.anhtuan.bookapp.domain;

import com.anhtuan.bookapp.request.AddBookReviewRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = BookReview.BOOK_REVIEW_COLLECTION)
public class BookReview {

    public static final String BOOK_REVIEW_COLLECTION = "book_review";
    public static final String ID = "_id";
    public static final String BOOK_ID = "book_id";
    public static final String AUTHOR = "author";
    public static final String REVIEW_STAR = "review_star";
    public static final String REVIEW_CONTENT = "review_content";
    public static final String REVIEW_TIME = "review_time";

    @Id
    private String id;

    @Field(BOOK_ID)
    private String bookId;

    @Field(AUTHOR)
    private String author;

    @Field(REVIEW_STAR)
    private double reviewStar;

    @Field(REVIEW_CONTENT)
    private String reviewContent;

    @Field(REVIEW_TIME)
    private long reviewTime;


    public BookReview(String id, String bookId, String author, double reviewStar, String reviewContent, long reviewTime) {
        this.id = id;
        this.bookId = bookId;
        this.author = author;
        this.reviewStar = reviewStar;
        this.reviewContent = reviewContent;
        this.reviewTime = reviewTime;
    }

    public BookReview(AddBookReviewRequest request) {
        this.bookId = request.getBookId();
        this.author = request.getAuthor();
        this.reviewStar = request.getReviewStar();
        this.reviewContent = request.getReviewContent();
    }
}
