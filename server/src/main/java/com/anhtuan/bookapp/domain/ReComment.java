package com.anhtuan.bookapp.domain;

import com.anhtuan.bookapp.request.AddReCommentRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = ReComment.RE_COMMENT_COLLECTION)
public class ReComment {
    public static final String RE_COMMENT_COLLECTION = "re_comment";
    public static final String PARENT_COMMENT_ID = "parent_comment_id";
    public static final String AUTHOR = "author";
    public static final String COMMENT_CONTENT = "comment_content";
    public static final String COMMENT_TIME = "comment_time";

    @Id
    private String id;

    @Field(PARENT_COMMENT_ID)
    private String parentCommentId;

    @Field(AUTHOR)
    private String author;

    @Field(COMMENT_CONTENT)
    private String commentContent;

    @Field(COMMENT_TIME)
    private long commentTime;

    public ReComment(String parentCommentId, String author, String commentContent, long commentTime) {
        this.parentCommentId = parentCommentId;
        this.author = author;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }

    public ReComment(AddReCommentRequest request) {
        this.parentCommentId = request.getParentCommentId();
        this.author = request.getAuthor();
        this.commentContent = request.getCommentContent();
    }
}
