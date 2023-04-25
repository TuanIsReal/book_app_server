package com.anhtuan.bookapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class NotificationMessage {
    private String recipientToken;
    private String title;
    private String body;
    private Map<String, String> data;

    public NotificationMessage(String recipientToken, String title, String body) {
        this.recipientToken = recipientToken;
        this.title = title;
        this.body = body;
    }
}
