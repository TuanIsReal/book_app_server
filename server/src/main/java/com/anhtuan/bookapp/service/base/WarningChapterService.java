package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.WarningChapter;

import java.util.List;

public interface WarningChapterService {
    void insert(WarningChapter warningChapter);

    void deleteByChapter(String chapter);

    List<WarningChapter> findAll();

    WarningChapter findWarningChapter(String chapterWarning);
}
