package com.anhtuan.bookapp.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddBookRequest extends BaseRequest{

    private String bookName;
    private String author;
    private String introduction;
    private String bookImage;
    private ArrayList<String> bookCategory;
    private int bookPrice;

    public AddBookRequest() {
    }

}
