package com.anhtuan.bookapp.response;

import lombok.ToString;

@ToString
public class RegisterResponse {

    private String userId;

    private String role = "member";

    public RegisterResponse() {
    }

    public RegisterResponse(String userId, String role) {
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
