package com.anhtuan.bookapp.repository.implement;


import com.anhtuan.bookapp.domain.WarningChapter;
import com.anhtuan.bookapp.repository.customize.WarningChapterCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class WarningChapterRepositoryImpl implements WarningChapterCustomizeRepository {
    private MongoTemplate mongoTemplate;

    @Override
    public void insert(WarningChapter warningChapter) {
        mongoTemplate.insert(warningChapter);
    }
}
