package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.User;

import java.util.List;
import java.util.Map;

public interface STFService {
    void createThumbnail(String folderPath, String pathImage);

    void createChapterText(String chapterContent, String fileName);

    String getChapterContent(String fileName);

    Map<String, String> getBookImagePathMap(List<Book> users);
}
