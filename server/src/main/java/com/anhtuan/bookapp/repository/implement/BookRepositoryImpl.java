package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.repository.base.BookRepository;
import com.anhtuan.bookapp.repository.customize.BookCustomizeRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepositoryImpl implements BookCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateBookImageByBookId(String bookId, String bookImage) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.ID).is(new ObjectId(bookId)));
        Update update = new Update();
        update.set(Book.BOOK_IMAGE, bookImage);
        update.set(Book.LAST_UPDATE_TIME, System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public List<Book> findBookByText(String text) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.BOOK_NAME).regex(text, "i"));
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public void updateTotalChapterById(String bookId, int totalChapter) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.ID).is(new ObjectId(bookId)));
        Update update = new Update();
        update.set(Book.TOTAL_CHAPTER, totalChapter);
        mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public void updateStarById(String id, double star) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.ID).is(new ObjectId(id)));
        Update update = new Update();
        update.set(Book.STAR, star);
        mongoTemplate.updateFirst(query, update, Book.class);
    }

}
