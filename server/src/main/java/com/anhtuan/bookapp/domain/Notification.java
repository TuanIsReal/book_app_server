package com.anhtuan.bookapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = Notification.NOTIFICATION_COLLECTION)
public class Notification {
    public static final String NOTIFICATION_COLLECTION = "notification";
    public static final String ID = "_id";
    public static final String USER_ID = "userId";
    public static final String BOOK_ID = "book_id";
    public static final String BODY = "body";
    public static final String IS_CLICK = "is_click";
    public static final String TIME = "time";

    @Id
    private String id;

    @Field(USER_ID)
    private String userId;

    @Field(BOOK_ID)
    private String bookId;

    @Field(BODY)
    private String body;

    @Field(IS_CLICK)
    private boolean isClick;

    @Field(TIME)
    private long time;

    public Notification(String userId, String bookId, String body, boolean isClick, long time) {
        this.userId = userId;
        this.bookId = bookId;
        this.body = body;
        this.isClick = isClick;
        this.time = time;
    }
}
