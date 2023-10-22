package com.anhtuan.bookapp.service.base;

public interface STFService {
    void createThumbnail(String folderPath, String pathImage);

    void createChapterText(String chapterContent, String fileName);

    String getChapterContent(String fileName);
}
