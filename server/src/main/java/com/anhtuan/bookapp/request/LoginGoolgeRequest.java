package com.anhtuan.bookapp.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginGoolgeRequest extends RegisterRequest{
    private String img;

    public LoginGoolgeRequest(String email, String password, String name, String ip, String img) {
        super(email, password, name, ip);
        this.img = img;
    }
}
