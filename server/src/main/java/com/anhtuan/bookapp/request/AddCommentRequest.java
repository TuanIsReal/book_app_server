package com.anhtuan.bookapp.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
public class AddCommentRequest {
    private String bookId;
    private String author;
    private String commentContent;
}
