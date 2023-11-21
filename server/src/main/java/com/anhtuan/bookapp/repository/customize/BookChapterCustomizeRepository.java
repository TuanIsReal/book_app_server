package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.BookChapter;

import java.util.List;

public interface BookChapterCustomizeRepository {
    void updateChapterContent(String chapterId, String chapterContent);

    void updateStatus(String chapterId, int status);

    List<BookChapter> findChapterByIds(List<String> ids);

}
