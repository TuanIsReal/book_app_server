package com.anhtuan.bookapp.repository.customize;

public interface BookChapterCustomizeRepository {
    void updateChapterContent(String chapterId, String chapterContent);

    void updateStatus(String chapterId, int status);

}
