package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Notification;
import com.anhtuan.bookapp.repository.customize.NotificationCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String>, NotificationCustomizeRepository {
    List<Notification> findNotificationsByUserIdOrderByTimeDesc(String userId);
}
