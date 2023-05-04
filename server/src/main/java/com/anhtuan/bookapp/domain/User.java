package com.anhtuan.bookapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = User.USER_COLLECTION)
public class User {
    public static final String USER_COLLECTION = "user";
    public static final String USER_ID = "_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String NAME = "name";
    public static final String AVATAR_IMAGE = "avatar_image";
    public static final String POINT = "point";
    public static final String IS_LOGGED = "is_logged";
    public static final String LAST_LOGIN_IP = "last_login_ip";
    public static final String IS_VERIFY = "is_verify";

    @Id
    private String id;

    @Field(EMAIL)
    private String email;

    @Field(PASSWORD)
    private String password;

    @Field(ROLE)
    private String role;

    @Field(NAME)
    private String name;

    @Field(AVATAR_IMAGE)
    private String avatarImage;

    @Field(LAST_LOGIN_IP)
    private String lastLoginIp;

    @Field(IS_LOGGED)
    private Boolean isLogged;

    @Field(POINT)
    private int point;

    @Field(IS_VERIFY)
    private boolean isVerify;

    public User() {
    }

    public User(String email, String password, String role, String name, String avatarImage, String lastLoginIp, Boolean isLogged, int point, boolean isVerify) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.avatarImage = avatarImage;
        this.lastLoginIp = lastLoginIp;
        this.isLogged = isLogged;
        this.point = point;
        this.isVerify = isVerify;
    }

}
