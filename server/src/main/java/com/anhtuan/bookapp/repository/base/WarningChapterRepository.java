package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.WarningChapter;
import com.anhtuan.bookapp.repository.customize.WarningChapterCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarningChapterRepository extends MongoRepository<WarningChapter, String>, WarningChapterCustomizeRepository {
    void deleteByChapter(String chapter);

    WarningChapter findWarningChapterByChapter(String chapter);
}
