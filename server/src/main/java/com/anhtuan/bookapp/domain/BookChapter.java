package com.anhtuan.bookapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = BookChapter.BOOK_CHAPTER_COLLECTION)
public class BookChapter extends Domain{
    public static final String ID = "_id";
    public static final String BOOK_CHAPTER_COLLECTION = "book_chapter";
    public static final String BOOK_ID = "book_id";
    public static final String CHAPTER_NUMBER = "chapter_number";
    public static final String CHAPTER_NAME = "chapter_name";
    public static final String CHAPTER_CONTENT = "chapter_content";
    public static final String UPLOAD_TIME= "upload_time";
    public static final String LAST_UPDATE_TIME = "last_update_time";
    public static final String STATUS = "status";

    @Field(BOOK_ID)
    private String bookId;

    @Field(CHAPTER_NUMBER)
    private int chapterNumber;

    @Field(CHAPTER_NAME)
    private String chapterName;

    @Field(CHAPTER_CONTENT)
    private String chapterContent;

    @Field(UPLOAD_TIME)
    private long uploadTime;

    @Field(LAST_UPDATE_TIME)
    private long lastUpdateTime;

    @Field(STATUS)
    private int status;

}
