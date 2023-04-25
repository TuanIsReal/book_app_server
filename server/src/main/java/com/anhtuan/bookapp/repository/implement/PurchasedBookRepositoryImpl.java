package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.PurchasedBook;
import com.anhtuan.bookapp.repository.customize.PurchasedBookCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PurchasedBookRepositoryImpl implements PurchasedBookCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateLastReadChapterByBookIdAndUserId(String bookId, String userId, int chapterNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where(PurchasedBook.BOOK_ID).is(bookId).and(PurchasedBook.USER_ID).is(userId));
        Update update = new Update();
        update.set(PurchasedBook.LAST_READ_CHAPTER, chapterNumber);
        mongoTemplate.updateFirst(query, update, PurchasedBook.class);
    }
}
