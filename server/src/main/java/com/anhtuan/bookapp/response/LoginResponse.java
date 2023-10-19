package com.anhtuan.bookapp.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String userId;

    private Integer role;

    public LoginResponse() {
    }

    public LoginResponse(String userId, Integer role) {
        this.userId = userId;
        this.role = role;
    }

}
