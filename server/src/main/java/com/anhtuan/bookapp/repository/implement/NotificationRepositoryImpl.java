package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Notification;
import com.anhtuan.bookapp.repository.customize.NotificationCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateIsClickById(String id, boolean isClick) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Notification.ID).is(id));
        Update update = new Update();
        update.set(Notification.IS_CLICK, isClick);
        mongoTemplate.updateFirst(query, update, Notification.class);
    }
}
