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

    public static final String IS_GOOGLE_LOGIN = "google_email";
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

    @Field(IS_GOOGLE_LOGIN)
    private Boolean isGoogleLogin;

    public Boolean getGoogleLogin() {
        return isGoogleLogin;
    }

    public void setGoogleLogin(Boolean googleLogin) {
        isGoogleLogin = googleLogin;
    }

    public User() {
    }

    public User(String email, String password, String role, String name, String avatarImage, String lastLoginIp, Boolean isLogged, int point) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.avatarImage = avatarImage;
        this.lastLoginIp = lastLoginIp;
        this.isLogged = isLogged;
        this.point = point;
    }

    public User(String email, String password, String role, String name, String avatarImage, String lastLoginIp, Boolean isLogged, int point, Boolean isGoogleLogin) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.avatarImage = avatarImage;
        this.lastLoginIp = lastLoginIp;
        this.isLogged = isLogged;
        this.point = point;
        this.isGoogleLogin = isGoogleLogin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
