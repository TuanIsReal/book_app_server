package com.anhtuan.bookapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReportRequest {
    private String bookId;
    private int type;
    private String content;
}
