package com.anhtuan.bookapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReCommentRequest {
    private String parentCommentId;
    private String author;
    private String commentContent;
}
