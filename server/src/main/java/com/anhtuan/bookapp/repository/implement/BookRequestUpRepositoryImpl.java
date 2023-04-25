package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.BookRequestUp;
import com.anhtuan.bookapp.repository.customize.BookRequestUpCustomizeRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BookRequestUpRepositoryImpl implements BookRequestUpCustomizeRepository {

    private MongoTemplate mongoTemplate;
    @Override
    public void updateBookImageById(String id, String bookImage) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BookRequestUp.ID).is(new ObjectId(id)));
        Update update = new Update();
        update.set(BookRequestUp.BOOK_IMAGE, bookImage);
        mongoTemplate.updateFirst(query, update, BookRequestUp.class);
    }

    @Override
    public void updateStatusById(String id, int status) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BookRequestUp.ID).is(new ObjectId(id)));
        Update update = new Update();
        update.set(BookRequestUp.STATUS, status);
        mongoTemplate.updateFirst(query, update, BookRequestUp.class);
    }
}
