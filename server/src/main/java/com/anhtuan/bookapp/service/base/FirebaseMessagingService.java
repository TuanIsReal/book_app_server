package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.NotificationMessage;

public interface FirebaseMessagingService {
    void sendNotificationByToken(NotificationMessage notificationMessage);
}
