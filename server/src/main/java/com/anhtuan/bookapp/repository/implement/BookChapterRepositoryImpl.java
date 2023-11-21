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

import java.util.ArrayList;
import java.util.List;

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
        update.set(BookChapter.LAST_UPDATE_TIME, System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, BookChapter.class);
    }

    @Override
    public void updateStatus(String chapterId, int status) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BookChapter.ID).is(new ObjectId(chapterId)));
        Update update = new Update();
        update.set(BookChapter.STATUS, status);
        update.set(BookChapter.LAST_UPDATE_TIME, System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, BookChapter.class);
    }

    @Override
    public List<BookChapter> findChapterByIds(List<String> ids) {
        Query query = new Query();
        List<ObjectId> idList = ids.stream().map(ObjectId::new).toList();
        query.addCriteria(Criteria.where(BookChapter.ID).in(idList));
        return mongoTemplate.find(query, BookChapter.class);
    }
}
