package com.anhtuan.bookapp.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddBookReviewRequest {
    private String bookId;
    private double reviewStar;
    private String reviewContent;
}
