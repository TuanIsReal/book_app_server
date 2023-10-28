package com.anhtuan.bookapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
    private String bookId;
    private String introduction;
    private ArrayList<String> bookCategory;
    private int bookPrice;
    private int freeChapter;
    private Boolean isFinish;
}
