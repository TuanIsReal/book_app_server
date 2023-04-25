package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Notification;

import java.util.List;

public interface NotificationService {
    void insertNotificationList(List<Notification> notificationList);
    void insertNotification(Notification notification);
    List<Notification> getNotificationByUserId(String userId);
    void updateIsClickById(String id, boolean isClick);
}
