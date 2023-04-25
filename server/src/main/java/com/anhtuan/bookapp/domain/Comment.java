package com.anhtuan.bookapp.domain;

import com.anhtuan.bookapp.request.AddCommentRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = Comment.COMMENT_COLLECTION)
public class Comment {

    public static final String COMMENT_COLLECTION = "comment";
    public static final String ID = "_id";
    public static final String BOOK_ID = "book_id";
    public static final String AUTHOR = "author";
    public static final String COMMENT_CONTENT = "comment_content";
    public static final String TOTAL_RE_COMMENT = "total_re_comment";
    public static final String COMMENT_TIME = "comment_time";

    @Id
    private String id;

    @Field(BOOK_ID)
    private String bookId;

    @Field(AUTHOR)
    private String author;

    @Field(COMMENT_CONTENT)
    private String commentContent;

    @Field(TOTAL_RE_COMMENT)
    private int totalReComment;

    @Field(COMMENT_TIME)
    private long commentTime;

    public Comment() {
    }

    public Comment(String id, String bookId, String author, String commentContent, long commentTime) {
        this.id = id;
        this.bookId = bookId;
        this.author = author;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }

    public Comment(AddCommentRequest request) {
        this.bookId = request.getBookId();
        this.author = request.getAuthor();
        this.commentContent = request.getCommentContent();
    }
}
