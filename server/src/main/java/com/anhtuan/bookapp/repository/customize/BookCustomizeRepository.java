package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.Book;

import java.util.List;

public interface BookCustomizeRepository {
    void updateBookImageByBookId(String bookId, String bookImage);

    List<Book> findBookByText(String text);

    void updateTotalChapterById(String bookId, int totalChapter);
    void updateStarById(String id, double star);


}
