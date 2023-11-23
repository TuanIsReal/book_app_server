package com.anhtuan.bookapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
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
    public static final String STATUS = "status";
    public static final String LAST_LOGIN_IP = "last_login_ip";
    public static final String IS_GOOGLE_LOGIN = "google_login";

    @Id
    private String id;

    @Field(EMAIL)
    private String email;

    @Field(PASSWORD)
    private String password;

    @Field(ROLE)
    private Integer role;

    @Field(NAME)
    private String name;

    @Field(AVATAR_IMAGE)
    private String avatarImage;

    @Field(LAST_LOGIN_IP)
    private String lastLoginIp;

    @Field(STATUS)
    private Integer status;

    @Field(POINT)
    private int point;

    @Field(IS_GOOGLE_LOGIN)
    private Boolean isGoogleLogin;

    public User(String email, String password, Integer role, String name, String avatarImage, String lastLoginIp, Integer status, int point) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.avatarImage = avatarImage;
        this.lastLoginIp = lastLoginIp;
        this.status = status;
        this.point = point;
    }

    public User(String email, String password, Integer role, String name, String avatarImage, String lastLoginIp, Integer status, int point, Boolean isGoogleLogin) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.avatarImage = avatarImage;
        this.lastLoginIp = lastLoginIp;
        this.status = status;
        this.point = point;
        this.isGoogleLogin = isGoogleLogin;
    }

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.name = user.getName();
        this.avatarImage = user.getAvatarImage();
        this.lastLoginIp = user.getLastLoginIp();
        this.status = user.getStatus();
        this.point = user.getPoint();
        this.isGoogleLogin = user.getIsGoogleLogin();
    }
}
