package com.anhtuan.bookapp.response;

import lombok.ToString;

@ToString
public class LoginResponse {

    private String userId;

    private String role = "member";

    public LoginResponse() {
    }

    public LoginResponse(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
