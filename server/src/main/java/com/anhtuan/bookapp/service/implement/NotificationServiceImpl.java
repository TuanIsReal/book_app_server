package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.Notification;
import com.anhtuan.bookapp.repository.base.NotificationRepository;
import com.anhtuan.bookapp.service.base.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Override
    public void insertNotificationList(List<Notification> notificationList) {
        notificationRepository.insert(notificationList);
    }

    @Override
    public void insertNotification(Notification notification) {
        notificationRepository.insert(notification);
    }

    @Override
    public List<Notification> getNotificationByUserId(String userId) {
        return notificationRepository.findNotificationsByUserIdOrderByTimeDesc(userId);
    }

    @Override
    public void updateIsClickById(String id, boolean isClick) {
        notificationRepository.updateIsClickById(id, isClick);
    }
}
