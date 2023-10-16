package com.anhtuan.bookapp.repository.implement;

import static com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.repository.customize.BookCustomizeRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public void updateStarById(String id, double star) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.ID).is(new ObjectId(id)));
        Update update = new Update();
        update.set(Book.STAR, star);
        mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public void updateTotalPurchasedById(String id, int totalPurchased) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.ID).is(new ObjectId(id)));
        Update update = new Update();
        update.set(Book.TOTAL_PURCHASED, totalPurchased);
        mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public void updateTotalReviewById(String id, int totalReview) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.ID).is(new ObjectId(id)));
        Update update = new Update();
        update.set(Book.TOTAL_REVIEW, totalReview);
        mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public List<Book> searchBookFilter(String sort, int order, int status, int post, List<String> category, int page) {
        Query query = new Query();
        if (status == FILTER_STATUS.COMPLETE){
            query.addCriteria(Criteria.where(Book.COMPLETE_BOOK).is(true));
        }
        if (status == FILTER_STATUS.WRITING){
            query.addCriteria(Criteria.where(Book.COMPLETE_BOOK).isNull());
        }
        if (post == FILTER_POST.ADMIN_POST){
            query.addCriteria(Criteria.where(Book.ADMIN_UP).is(true));
        }
        if (post == FILTER_POST.USER_POST){
            query.addCriteria(Criteria.where(Book.ADMIN_UP).isNull());
        }
        if (category != null && category.size() > 0){
            query.addCriteria(Criteria.where(Book.BOOK_CATEGORY).in(category));
        }
        if (!sort.equals("all") && order == 1){
            query.with(Sort.by(Sort.Order.asc(sort)));
        }
        if (!sort.equals("all") && order == -1){
            query.with(Sort.by(Sort.Order.desc(sort)));
        }
        query.skip((page - 1) * 10L).limit(10);
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public void increaseTotalChapter(String bookId) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Book.ID).is(new ObjectId(bookId)));
        Update update = new Update();
        update.inc(bookId, 1);
        mongoTemplate.updateFirst(query, update, Book.class);
    }

}
