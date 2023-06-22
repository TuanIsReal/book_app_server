package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.NotificationMessage;
import com.anhtuan.bookapp.service.base.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService {

    private FirebaseMessaging firebaseMessaging;

    @Override
    public int sendNotificationByToken(NotificationMessage notificationMessage) {

        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();

        Message message =  Message
                .builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return 100;
        } catch (FirebaseMessagingException e){
            e.printStackTrace();
            return 117;
        }
    }
}
