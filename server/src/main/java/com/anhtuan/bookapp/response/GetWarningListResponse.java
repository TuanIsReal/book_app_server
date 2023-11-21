package com.anhtuan.bookapp.response;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.BookChapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWarningListResponse {
    private BookChapter chapter;
    private BookChapter chapterReport;
    private Book book;
    private Book bookReport;
}
