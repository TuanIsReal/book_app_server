package com.anhtuan.bookapp.domain;


import com.anhtuan.bookapp.request.AddBookChapterRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = BookChapter.BOOK_CHAPTER_COLLECTION)
public class BookChapter {

    public static final String BOOK_CHAPTER_COLLECTION = "book_chapter";
    public static final String BOOK_ID = "book_id";
    public static final String CHAPTER_NUMBER = "chapter_number";
    public static final String CHAPTER_NAME = "chapter_name";
    public static final String CHAPTER_CONTENT = "chapter_content";
    public static final String UPLOAD_TIME= "upload_time";
    public static final String LAST_UPDATE_TIME = "last_update_time";

    @Id
    private String id;

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

    public BookChapter() {
    }

    public BookChapter(String bookId, int chapterNumber, String chapterName, String chapterContent, long uploadTime, long lastUpdateTime) {
        this.bookId = bookId;
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.chapterContent = chapterContent;
        this.uploadTime = uploadTime;
        this.lastUpdateTime = lastUpdateTime;
    }

}
