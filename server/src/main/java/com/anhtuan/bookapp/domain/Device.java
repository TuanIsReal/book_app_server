package com.anhtuan.bookapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = Device.DEVICE_COLLECTION)
public class Device {
    public static final String DEVICE_COLLECTION = "device";
    public static final String ID = "_id";
    public static final String USER_ID = "user_id";
    public static final String DEVICE_TOKEN = "device_token";

    @Id
    private String id;

    @Field(USER_ID)
    private String userId;

    @Field(DEVICE_TOKEN)
    private String deviceToken;

    public Device(String userId, String deviceToken) {
        this.userId = userId;
        this.deviceToken = deviceToken;
    }
}
