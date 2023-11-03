package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.WarningChapter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarningChapterRepository extends MongoRepository<WarningChapter, String> {
    void deleteByChapter(String chapter);

    WarningChapter findWarningChapterByChapter(String chapter);
}
