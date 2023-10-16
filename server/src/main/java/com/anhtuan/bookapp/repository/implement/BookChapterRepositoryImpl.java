package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.BookChapter;
import com.anhtuan.bookapp.repository.customize.BookChapterCustomizeRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BookChapterRepositoryImpl implements BookChapterCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateChapterContent(String chapterId, String chapterContent) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BookChapter.ID).is(new ObjectId(chapterId)));
        Update update = new Update();
        update.set(BookChapter.CHAPTER_CONTENT, chapterContent);
        mongoTemplate.updateFirst(query, update, BookChapter.class);
    }
}
