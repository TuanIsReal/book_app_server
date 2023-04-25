package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.NotificationMessage;

public interface FirebaseMessagingService {
    public int sendNotificationByToken(NotificationMessage notificationMessage);
}
