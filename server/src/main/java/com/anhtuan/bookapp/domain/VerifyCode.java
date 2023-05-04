package com.anhtuan.bookapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = VerifyCode.VERIFY_CODE_COLLECTION)
public class VerifyCode {
    public static final String VERIFY_CODE_COLLECTION = "verify_code";
    public static final String ID = "_id";
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String TYPE = "type";
    public static final String CODE = "code";
    public static final String TIME = "time";

    @Id
    private String id;

    @Field(USER_ID)
    private String userId;

    @Field(EMAIL)
    private String email;

    @Field(TYPE)
    private int type;

    @Field(CODE)
    private String code;

    @Field(TIME)
    private long time;

    public VerifyCode(String userId, String email, int type, String code, long time) {
        this.userId = userId;
        this.email = email;
        this.type = type;
        this.code = code;
        this.time = time;
    }
}
