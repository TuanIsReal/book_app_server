package com.anhtuan.bookapp.response;

import lombok.Data;
import lombok.ToString;

@Data
public class RegisterResponse {

    private String userId;

    private Integer role;

    public RegisterResponse() {
    }

    public RegisterResponse(String userId, Integer role) {
        this.userId = userId;
        this.role = role;
    }

}
