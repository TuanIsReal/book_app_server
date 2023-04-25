package com.anhtuan.bookapp.request;

import lombok.Data;

@Data
public class AddBookChapterRequest {
    private String bookName;
    private int chapterNumber;
    private String chapterName;
    private String chapterContent;

    public AddBookChapterRequest() {
    }
}
