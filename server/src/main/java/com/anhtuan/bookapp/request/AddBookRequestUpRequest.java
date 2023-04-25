package com.anhtuan.bookapp.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class AddBookRequestUpRequest {
    private String userPost;
    private String bookName;
    private String author;
    private String introduction;
    private String bookImage;
    private ArrayList<String> bookCategory;
    private int bookPrice;
}
